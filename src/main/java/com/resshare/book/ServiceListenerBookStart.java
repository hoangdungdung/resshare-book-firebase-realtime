package com.resshare.book;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.resshare.book.addbook.LoadFormAddBookListener;
import com.resshare.book.bookcase.LoadFormMyBoocaseListener;
import com.resshare.book.bookcase.LoadFormViewMyBookcaseListener;
import com.resshare.book.bookcase.RemoveBookItem;
import com.resshare.book.profile.AddProfileListener;
import com.resshare.book.profile.LoadFormProfileListener;
import com.resshare.book.search.Blacklist;
import com.resshare.book.search.LoadFormSearchingListener;
import com.resshare.book.search.MessageRequestBookLoadFormListener;
import com.resshare.book.search.ObjectionableContent;
import com.resshare.book.search.RatingBook;
import com.resshare.book.search.ReportUser;
import com.resshare.book.search.SeclectBookService;
import com.resshare.book.transaction.BookSupplierAcceptOrderListener;

@SpringBootApplication // (scanBasePackages = { "com.websystique.springboot" }) // same as
						// @Configuration
						// @EnableAutoConfiguration @ComponentScan
						// combined
public class ServiceListenerBookStart {

	public static void startListener() {

		FirebaseBookServices firebaseServices = new FirebaseBookServices();
		firebaseServices.onStart();

		// BooksItemsNoaService booksItemsNoaService = new BooksItemsNoaService();
		// booksItemsNoaService.onStart();

		LoadFormAddBookListener loadFormAddBookListener = new LoadFormAddBookListener();
		loadFormAddBookListener.onStart();

		LoadFormSearchingListener loadFormSearchingListener = new LoadFormSearchingListener();
		loadFormSearchingListener.onStart();

		SeclectBookService seclectBookService = new SeclectBookService();
		seclectBookService.onStart();

		BookSupplierAcceptOrderListener supplierAcceptOrderListener = new BookSupplierAcceptOrderListener();
		supplierAcceptOrderListener.onStart();

		LoadFormMyBoocaseListener loadFormMyBoocaseListener = new LoadFormMyBoocaseListener();
		loadFormMyBoocaseListener.onStart();

		LoadFormViewMyBookcaseListener loadFormViewMyBookcaseListener = new LoadFormViewMyBookcaseListener();
		loadFormViewMyBookcaseListener.onStart();

		LoadFormProfileListener loadFormSupplierOxyListener = new LoadFormProfileListener();
		loadFormSupplierOxyListener.onStart();

		AddProfileListener addProfileListener = new AddProfileListener();
		addProfileListener.onStart();

		MessageRequestBookLoadFormListener messageRequestBookLoadFormListener = new MessageRequestBookLoadFormListener();
		messageRequestBookLoadFormListener.onStart();

		RemoveBookItem removeBookItem = new RemoveBookItem();
		removeBookItem.onStart();

		RatingBook ratingBook = new RatingBook();
		ratingBook.onStart();
		

		Blacklist blacklist = new Blacklist();
		blacklist.onStart();

		ObjectionableContent objectionableContent = new ObjectionableContent();
		objectionableContent.onStart();

		ReportUser reportUser = new ReportUser();
		reportUser.onStart();

		// BookService bookService = new BookService();
		// bookService.onStart();

	}

}
