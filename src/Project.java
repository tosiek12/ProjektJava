import java.awt.Color;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import net.htmlparser.jericho.*;

public class Project {
	private AtomicInteger siteKey = new AtomicInteger(0);
	private AtomicInteger whatToDo = new AtomicInteger(0);	//1 - pobierz / 2 - zakoncz / 0 - nic nie rób
	private CountDownLatch doneSignal;
	
	private JTextPane txtPane;
	private Semaphore txtPane_semaphore = new Semaphore(1);
	private JProgressBar progressBar;
	private Semaphore progressBar_semaphore = new Semaphore(1);
	private JTextField txtOstatniaAktualizacja;
	private Logger log = Logger.getLogger(this.getClass().getName()); 
	{
		//PropertyConfigurator.configure("log4j.properties");
	};

	private class Downloader implements Callable<Integer>{
		private final URL url;
		private final String name;

		public Downloader(URL url, String name) {
			this.url = url;
			this.name = name;
			log.debug("("+name+")Created Object: Downloader");
		}

		private String readAll(Reader reader) throws IOException {
			StringBuilder builder = new StringBuilder();
			int read = 0;
			log.debug("("+name+")Downloader - readAll Started");
			while ((read = reader.read()) != -1) {
				builder.append((char) read);
			}
			log.debug("("+name+")Downloader - readAll Finished");
			return builder.toString();
		}

		private void parseOnet (Source src ) {
			
		}
		
		private void parseWP (Source src ) {
			
		}
		
		@Override
		public Integer call() throws Exception {
			Integer size = 0;
			try {
				InputStream stream = null;
				Reader reader = null;
				try {
					
					URLConnection conn = url.openConnection();
					stream = conn.getInputStream();
					reader = new BufferedReader(new InputStreamReader(stream));
					
					//reader = new BufferedReader(new InputStreamReader(url.openStream()));
					Source src = null;					
					try {
						src =  new Source(url.openConnection());
						log.debug("("+name+")Downloader - Otworzono polaczenie");
						//src = new Source(file.toURI().toURL());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
						
					
					// TODO: Parsowanie - start
					log.debug("("+name+")Downloader - Start parsowania strony");
					List<String> newsHeaders = new ArrayList<String>();
					
					
					if(url.toString().equals("http://www.interia.pl/")) {
						System.err.println("onet");				
						//tylko <div > </div>
						List<Element> elements = null;
						elements = src.getAllElements(HTMLElementName.DIV);
						//System.out.println("\n" + "Only: " + elements.size() + "\n");

						
						//tylko te id = bxWiadomosci			
						List<Element> newElements = new ArrayList<Element>();
						for (Element e : elements) {
							String res = e.getAttributeValue("class");
							//System.out.println(e + "\n");
							if (res!=null && res.equals("main")) {
								newElements.add(e);
							}
						}
						
						log.debug("("+name+")Parsowanie: Only "+elements.size()+" elements founded");
						elements.clear();
						elements.addAll(newElements);
						newElements.clear();
						/*
						//Tylko UL, których class = dot
						for (Element e : elements) {
							List<Element> temp = e.getAllElements(HTMLElementName.UL);
							for (Element temp_e : temp) {
								String res = temp_e.getAttributeValue("class");
								if (res!=null && res.equals("dot")) {
									newElements.add(temp_e);
								}
							}
						}
						
						log.debug("("+name+")Parsowanie: Only "+elements.size()+" elements founded");
						elements.clear();
						elements.addAll(newElements);
						newElements.clear();
						*/
						
						//Same wiadomoœci
						for (Element e : elements) {
							List<Element> temp = e.getAllElements(HTMLElementName.A);
							for (Element temp_e : temp) {
								if(!temp_e.getContent().getAllElements().isEmpty()){
									newsHeaders.add(temp_e.getContent()+".\n");
								}
							}
						}
						log.debug("("+name+")Parsowanie: Only "+newsHeaders.size()+" news headers founded");
						
						elements.clear();
						newElements.clear();
						
						
						newsHeaders.clear();
					}
						
					else if(url.toString().equals("http://www.wp.pl/index.html")) {
						System.err.println("wp");
						//tylko <div > </div>
						List<Element> elements = null;
						elements = src.getAllElements(HTMLElementName.DIV);
						//System.out.println("\n" + "Only: " + elements.size() + "\n");

						//tylko te id = bxWiadomosci			
						List<Element> newElements = new ArrayList<Element>();
						for (Element e : elements) {
							String res = e.getAttributeValue("id");
							if (res!=null && res.equals("bxWiadomosci")) {
								newElements.add(e);
							}
						}
						
						log.debug("("+name+")Parsowanie: Only "+elements.size()+" elements founded");
						elements.clear();
						elements.addAll(newElements);
						newElements.clear();

						//Tylko UL, których class = dot
						for (Element e : elements) {
							List<Element> temp = e.getAllElements(HTMLElementName.UL);
							for (Element temp_e : temp) {
								String res = temp_e.getAttributeValue("class");
								if (res!=null && res.equals("dot")) {
									newElements.add(temp_e);
								}
							}
						}
						
						log.debug("("+name+")Parsowanie: Only "+elements.size()+" elements founded");
						elements.clear();
						elements.addAll(newElements);
						newElements.clear();
						
						//Same wiadomoœci
						for (Element e : elements) {
							List<Element> temp = e.getAllElements(HTMLElementName.A);
							for (Element temp_e : temp) {
								if(temp_e.getContent().getAllElements().isEmpty()){
									newsHeaders.add(temp_e.getContent()+".\n");
								}
							}
						}
						log.debug("("+name+")Parsowanie: Only "+newsHeaders.size()+" news headers founded");
						
						elements.clear();
						newElements.clear();
					}
					
					for (String e : newsHeaders) {
						txtPane_semaphore.acquire();
						String pause = "-------------------------------------------------\n";
						Window.appendToPane(txtPane, e, Color.DARK_GRAY);
						Window.appendToPane(txtPane, pause, Color.MAGENTA);
						txtPane_semaphore.release();
					}
					log.debug("("+name+")Downloader - Koniec parsowania strony");
					// TODO: Parsowanie strony. - koniec.
					
					
					String result = readAll(reader);					
					size = result.length();
					
					log.debug("("+name+")" + siteKey.getAndIncrement() + "Read "+result.length()+" characters from "+url+"\n");

					txtPane_semaphore.acquire();
					//Window.appendToPane(txtPane, name + "." + result.length() +"\n", Color.red);
					txtPane_semaphore.release();
					
					progressBar_semaphore.acquire();
					int copy = progressBar.getValue();
					progressBar.setValue(copy + 1);
					progressBar_semaphore.release();
					
				} finally {
					if (reader != null)
						reader.close();
					
					whatToDo.set(2);
					doneSignal.countDown();
					log.debug("("+name+")Zakoñczono - czekam na barierze");
					
				}
			} catch (IOException e) {
				System.err.println(e);
			}
			log.debug("("+name+")Usuniecie procesu");
			return size;
		}
	}

	public void runIt(String[] addresses) {
		Window okno = new Window(whatToDo);
		okno.createWindow();
		
		txtPane = okno.getTxtWiadomosci();
		progressBar = okno.getProgressBar();
		txtOstatniaAktualizacja = okno.getTxtOstatniaAktualizacja();
		progressBar.setMaximum(addresses.length);

		log.info("Project: Poprawenie zaincjalizowano - czekam na instrukcje.");
		while(whatToDo.get() != 2) {
			if(whatToDo.get() == 1){
				log.debug("Project: Zmiana WhatToDo na 1 - rozpoczynam pobieranie.");
				try {
					pobierz(addresses);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					whatToDo.set(2);
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.debug("Project: Zmiana WhatToDo na 2 - koñcze projekt.");
		
	}
	
	private void pobierz(String[] addresses) throws MalformedURLException {
		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		ExecutorService executor = Executors.newFixedThreadPool(3);
		log.info("Following web pages number will be downloaded: "
				+ addresses.length);
		doneSignal = new CountDownLatch(addresses.length); // Stworz bariere, na
															// ktorej bede
															// czeka³ na
															// zakonczenie
															// wszystkich
															// pobierañ.
		int it = 0;
		for (String s : addresses) {
			Future<Integer> submit = executor
					.submit(new Downloader(new URL(s),String.valueOf(it)));
			list.add(submit);
			it++;
		}

		try {
			log.debug("Project: Czekam na zakoñczenie pobierania");
			doneSignal.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		log.debug("Project: Pobieranie zakoñczone");
		
		whatToDo.set(0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm | dd.MM.yyyy");
		
		String todayDate = sdf.format(new Date());
		txtOstatniaAktualizacja.setText("Ostatnia aktualizacja: " + todayDate);
		
		progressBar.setVisible(false);
		progressBar.setValue(0);
		
		
		int i = 0;
		for (Future<Integer> temp : list) {
			try {
				log.info("Project: W¹tek nr"+ i++ + " pobra³: " + temp.get() + " znaków");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		executor.shutdown();
		log.debug("Project: Zamknieto FixedThreadPool");
	}
	
	public static void main(String[] args) {
		
		// TODO: Wczytanie listy stron z informacjami z pliku.
		String[] addresses = {
				"http://www.vogella.com/tutorials/JavaConcurrency/article.html",
				"http://www.orangecoat.com/smaller-and-faster-web-pages-with-gzip-deflate-and-apache",
				"http://support.sas.com/documentation/cdl/en/lrdict/64316/HTML/default/viewer.htm",
				"http://www.vogella.com/tutorials/JavaConcurrency/article.html",
				"http://www.orangecoat.com/smaller-and-faster-web-pages-with-gzip-deflate-and-apache",
				"http://www.vogella.com/tutorials/JavaConcurrency/article.html",
				"http://www.orangecoat.com/smaller-and-faster-web-pages-with-gzip-deflate-and-apache",
				"http://www.vogella.com/tutorials/JavaConcurrency/article.html",
				"http://www.orangecoat.com/smaller-and-faster-web-pages-with-gzip-deflate-and-apache",
				"http://www.orangecoat.com/smaller-and-faster-web-pages-with-gzip-deflate-and-apache",
				"http://support.sas.com/documentation/cdl/en/lrdict/64316/HTML/default/viewer.htm",
				"http://www.vogella.com/tutorials/JavaConcurrency/article.html",
				"http://www.orangecoat.com/smaller-and-faster-web-pages-with-gzip-deflate-and-apache",
				"http://www.vogella.com/tutorials/JavaConcurrency/article.html",
				"http://www.orangecoat.com/smaller-and-faster-web-pages-with-gzip-deflate-and-apache",
				"http://www.vogella.com/tutorials/JavaConcurrency/article.html",
				"http://www.orangecoat.com/smaller-and-faster-web-pages-with-gzip-deflate-and-apache"};

		String[] addresses2 = {"http://www.interia.pl/"};
		String[] addresses3 = {"http://www.wp.pl/index.html","http://www.interia.pl/","http://www.wp.pl/index.html","http://www.wp.pl/index.html","http://www.interia.pl/"};

		Project programme = new Project();
		programme.runIt(addresses3);
		
	}
}



