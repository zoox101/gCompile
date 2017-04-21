package Backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@SuppressWarnings("unused")
public class ZZZTesting {
	
	public static void main(String args[]) throws IOException {
		
		System.out.println("Running");	
		FileStorage store = new FileStorage(new File("/Users/WilliamBooker/Desktop/AAAGDocOutputs/"));
		
		/* */
		store.add("https://docs.google.com/document/d/1wMpESdfLqrdCwabn21WIoWPd4lfuwJGhTC3Qilpvzko/edit?usp=sharing");
		store.add("https://docs.google.com/document/d/1tenWL80YcIKQOVlb6Go1ZTnSfTy1iafyZDrrhftwT2U/edit?usp=sharing");
		store.add("https://docs.google.com/document/d/1ocnxqVC9277CzHDbNfTknclTDTS8dCDrhg0t1jNbYjE/edit?usp=sharing");
		store.add("https://docs.google.com/document/d/1NtkTce2Fm4-IaRIy27CL1M8Gs9si_LAtixmma2-3P7Q/edit?usp=sharing");
		/* */
		
		System.out.println(GCompile.compileAndRun(store, store.src, store.bin));
		System.out.println("Finished");
	}
}
