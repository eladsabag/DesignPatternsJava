/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import Memento.LinkedHashMapMemento;
import Memento.MapMemento;
import Memento.TreeMapMemento;
import Observer.Message;
import Observer.Receiver;
import Observer.Sender;

public class Store implements Iterable<Entry<String, Product>>, Sender, Receiver {
	private static Store single_instance = null;
	public static final String F_NAME = "products.txt";
	private int savingChoice = -1;
	private Map<String, Product> allProducts;
	private MapMemento memento;
	private Comparator<String> comparator;
	private ArrayList<Receiver> receivers;
	private ArrayList<String> names;
	private int totalProfit;

	// constructor
	private Store() {
		receivers = new ArrayList<Receiver>();
		names = new ArrayList<String>();
		this.totalProfit = 0;
	}

	// singleton
	public static Store getInstance() {
		if (single_instance == null)
			single_instance = new Store();
		return single_instance;
	}

	// memento
	public void setMemento(MapMemento memento) {
		this.memento = memento;
	}

	public MapMemento getMemento() {
		return memento;
	}

	public void save() {
		if (savingChoice == -1)
			memento = null;
		else if (savingChoice == Model.ALPHABETICAL || savingChoice == Model.REVERSED_ALPHABETICAL)
			memento = new TreeMapMemento((TreeMap<String, Product>) allProducts, comparator);
		else
			memento = new LinkedHashMapMemento((LinkedHashMap<String, Product>) allProducts);
	}

	// setters & getters
	public ArrayList<String> getNames() {
		return names;
	}

	public ArrayList<Receiver> getReceivers() {
		return receivers;
	}

	public Map<String, Product> getAllProducts() {
		return allProducts;
	}

	public void setAllProduct(Map<String, Product> map) {
		allProducts = map;
		if (allProducts == null) {
			savingChoice = -1;
			setComparator(savingChoice);
		}
	}

	public void setTotalProfit(int total) {
		this.totalProfit = total;
	}

	public int getTotalProfit() {
		return totalProfit;
	}

	public int getSavingChoice() {
		return savingChoice;
	}

	public void setSavingChoice(int savingChoice) { // 1 - A-Z , 2 - Z-A , 3 - Regular
		setComparator(savingChoice);
		if (savingChoice == Model.ALPHABETICAL)
			allProducts = new TreeMap<>(comparator);
		else if (savingChoice == Model.REVERSED_ALPHABETICAL)
			allProducts = new TreeMap<>(comparator);
		else
			allProducts = new LinkedHashMap<>();
		this.savingChoice = savingChoice;
	}

	public void setComparator(int type) {
		if (type == Model.ALPHABETICAL) {
			comparator = new Comparator<String>() {

				@Override
				public int compare(String s1, String s2) {
					return s1.compareTo(s2);
				}
			};
		} else if (type == Model.REVERSED_ALPHABETICAL) {
			comparator = new Comparator<String>() {

				@Override
				public int compare(String s1, String s2) {
					return s2.compareTo(s1);
				}
			};
		} else
			comparator = null;
	}

	public void addProductProfit(int productProfit) {
		totalProfit += productProfit;
	}

	// Observer
	@Override
	public void sendMSG(Receiver r, String msg) {
		if (r instanceof Client)
			r.receiveMSG(this, new Message(msg));
	}

	@Override
	public void receiveMSG(Sender s, Message msg) {
		names.add(msg.getMsg());
	}

	public void addReceiver(Client c) {
		if (!receivers.contains(c))
			receivers.add(c);
	}

	public void removeReceiver(Client c) {
		for (int i = 0; i < receivers.size(); i++) {
			if (receivers.get(i) instanceof Client) {
				if (((Client) receivers.get(i)).equals(c)) {
					receivers.remove(receivers.get(i));
					return;
				}
			}
		}
	}

	public void addProduct(String k, Product p) {
		Product tmp = allProducts.get(k);
		if (tmp == null) {
			if (p.getClient().isNotifications())
				addReceiver(p.getClient());
		}
		allProducts.put(k, p);
		addProductProfit(p.getStorePrice() - p.getSupplierPrice());
	}

	public void clear() {
		allProducts = null;
		totalProfit = 0;
		savingChoice = -1;
		memento = null;
		receivers.clear();
	}

	public void writeMapToFile() {
		try (RandomAccessFile raf = new RandomAccessFile(F_NAME, "rw")) {
			raf.setLength(0);
			if (allProducts == null)
				return;
			else {
				Iterator<Entry<String, Product>> it = allProducts.entrySet().iterator();
				raf.writeInt(savingChoice);
				while (it.hasNext()) {
					Entry<String, Product> en = it.next();
					writeClient(raf, en.getValue().getClient());
					writeProduct(raf, en.getValue());
					raf.writeUTF(en.getKey());
				}
			}
			raf.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public int writeClient(RandomAccessFile raf, Client c) {
		try {
			int byteWtiring = 0;
			raf.writeUTF(c.getName());
			byteWtiring += c.getName().length() + 2;
			raf.writeUTF(c.getMobileNumber());
			byteWtiring += c.getMobileNumber().length() + 2;
			raf.writeBoolean(c.isNotifications());
			byteWtiring += 1;
			return byteWtiring;
		} catch (IOException e) {
			return -1;
		}
	}

	public int writeProduct(RandomAccessFile raf, Product p) {
		try {
			int byteWriting = 0;
			raf.writeUTF(p.getProductName());
			byteWriting += p.getProductName().length() + 2;
			raf.writeInt(p.getSupplierPrice());
			byteWriting += 4;
			raf.writeInt(p.getStorePrice());
			byteWriting += 4;
			return byteWriting;
		} catch (IOException e) {
			return -1;
		}
	}

	public void readMapFromFile() {
		clear();
		Iterator<Entry<String, Product>> it = iterator();
		Entry<String, Product> en;
		while (it.hasNext()) {
			en = it.next();
			allProducts.put(en.getKey(), en.getValue());
			totalProfit += en.getValue().getStorePrice() - en.getValue().getSupplierPrice();
			if (en.getValue().getClient().isNotifications())
				addReceiver(en.getValue().getClient());
		}
	}

	public int clientBytes(Client c) {
		int bytes = 0;
		bytes += c.getName().length() + 2;
		bytes += c.getMobileNumber().length() + 2;
		return bytes + 1; // 1 - boolean byte size
	}

	public int productBytes(Product p) {
		int bytes = 0;
		bytes += p.getProductName().length() + 2;
		return bytes + 8; // 8 - int byte size X 2
	}

	// file iterator
	private class ConcreteIterator implements Iterator<Entry<String, Product>> {
		private int readPointer = 0, writePointer = 0;
		private int entryLength = 0;
		private Entry<String, Product> en;
		private RandomAccessFile raf;

		// iterator constructor
		public ConcreteIterator() {
			try {
				raf = new RandomAccessFile(F_NAME, "rw");
				raf.seek(0);
			} catch (Exception e) {
				return;
			}
		}

		// iterator methods
		@Override
		public void remove() {
			try {
				if (raf.length() == writePointer) {
					raf.setLength(writePointer - entryLength);
					if (raf.length() == 4) {
						raf.setLength(0);
						writePointer -= 4;
						raf.close();
					}
				}
				totalProfit -= (en.getValue().getStorePrice() - en.getValue().getSupplierPrice());
				if (en.getValue().getClient().isNotifications())
					removeReceiver(en.getValue().getClient());
				writePointer -= entryLength;
			} catch (IOException e) {
				return;
			}
		}

		@Override
		public boolean hasNext() {
			try {
				return raf.getFilePointer() + 4 < raf.length();
			} catch (IOException e) {
				return false;
			}
		}

		@Override
		public Entry<String, Product> next() {
			try {
				if (raf.getFilePointer() == 0) {
					setSavingChoice(raf.readInt());
					readPointer += 4;
				}
				Client c = new Client(raf.readUTF(), raf.readUTF(), raf.readBoolean());
				readPointer += clientBytes(c);
				Product p = new Product(raf.readUTF(), raf.readInt(), raf.readInt(), c);
				readPointer += productBytes(p);
				String k = raf.readUTF();
				readPointer += k.length() + 2;
				en = new Entry<String, Product>() {
					private Product value = p;
					private String key = k;

					@Override
					public String getKey() {
						return key;
					}

					@Override
					public Product getValue() {
						return value;
					}

					@Override
					public Product setValue(Product value) {
						Product old = this.value;
						this.value = value;
						return old;
					}
				};
				raf.seek(writePointer);
				if (raf.length() - raf.getFilePointer() == raf.length()) {
					raf.writeInt(savingChoice);
					writePointer += 4;
				}
				entryLength = writeClient(raf, en.getValue().getClient());
				entryLength += writeProduct(raf, en.getValue());
				raf.writeUTF(en.getKey());
				entryLength += en.getKey().length() + 2;
				writePointer += entryLength;
				raf.seek(readPointer);
				if (raf.length() == readPointer)
					raf.setLength(writePointer);
				return en;
			} catch (IOException e) {
				return null;
			}
		}

	}

	@Override
	public Iterator<Entry<String, Product>> iterator() {
		return new ConcreteIterator();
	}

}
