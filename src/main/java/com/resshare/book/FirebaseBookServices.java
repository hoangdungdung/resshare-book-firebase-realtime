package com.resshare.book;

import com.resshare.book.addbook.BookService;
import com.resshare.book.addbook.BooksItemsNoaService;
import com.resshare.book.search.SearchBookService;
import com.resshare.book.search.SearchServiceGennaral;
import com.resshare.book.transaction.AcceptRentBookService;
import com.resshare.conversation.service.ConversationSearchUserService;
import com.resshare.conversation.service.MessageService;
import com.resshare.conversation.service.MessageServiceCount;
//import com.resshare.conversation.service.UserChannelCacheService;
import com.sshare.book.map.LocationTmpService;
import com.sshare.book.map.ResquesDistanceService;

import service.AcceptSaleBookService;
import service.AuthorService;
import service.BookNoa2Service;
import service.BookSitesService;
import service.BooksBarcodeService;
import service.BoomService;
import service.BorrowItemsChangeStateFlowService;
import service.BorrowStateFlowService;
import service.CategoryService;
import service.FirebaseUsersService;
//import service.KeyAuthorService;
////import service.KeyBookService;
//import service.KeyCategoryService;
//import service.KeyUsersService;
import service.LoanItemsChangeStateFlowService;
import service.LoanStateFlowService;
import service.PurchaseItemsChangeStateFlowService;
import service.PurchaseStateFlowService;
import service.RatingService;
import service.SaleItemsChangeStateFlowService;
import service.SearchAuthorService;
import service.SearchBomPhoneNumberService;
import service.SearchCategoryService;
import service.SearchUserService;
import service.SeleStateFlowService;
import service.ServiceBase;
import service.TranRentService;
import service.UsersKeyService;
import service.UsersService;

public class FirebaseBookServices {
	public void onStop() {
		System.out.println("main execution  stop ");
		// resquestSrc.onStop();

		bookService.onStop();
		bookNoa2svc.onStop();
		// searchService3.onStop();

		// keyUsersService.onStop();

		usersService.onStop();

		// keyAuthorsService.onStop();

		authorsService.onStop();

		// keyCategorysService.onStop();

		categorysService.onStop();

		searchCategoryService.onStop();

		searchAuthorService.onStop();

		FirebaseUsersService.onStop();

		PurchaseItemsChangeStateFlowService.onStop();

		PurchaseStateFlowService.onStop();

		saleItemsChangeStateFlowService.onStop();

		saleStateFlowService.onStop();

		borrowItemsChangeStateFlowService.onStop();

		borrowStateFlowService.onStop();

		loanItemsChangeStateFlowService.onStop();

		loanStateFlowService.onStop();

		acceptSaleBookService.onStop();

		acceptBookService.onStop();

		tranRentService.onStop();

		searchService.onStop();
		booksBarcode.onStop();
		searchUserService.onStop();
		searchService2.onStop();
		// svb.onStop();
		// serviceFireDistance.onStop();

		locationTmpService.onStop();
		resquesServiceFireBook2.onStop();

		// userChannelCacheService.onStop();
		messageService.onStop();
		messageServiceCount.onStop();

	}

	BooksItemsNoaService bookService;
	// KeyBookService searchService3;
	// KeyUsersService keyUsersService;
	UsersService usersService;
	// KeyAuthorService keyAuthorsService;
	AuthorService authorsService;

	// KeyCategoryService keyCategorysService;
	CategoryService categorysService;
	SearchCategoryService searchCategoryService;
	SearchAuthorService searchAuthorService;
	private FirebaseUsersService FirebaseUsersService;
	private PurchaseItemsChangeStateFlowService PurchaseItemsChangeStateFlowService;
	private PurchaseStateFlowService PurchaseStateFlowService;
	private SaleItemsChangeStateFlowService saleItemsChangeStateFlowService;
	private SeleStateFlowService saleStateFlowService;
	private BorrowItemsChangeStateFlowService borrowItemsChangeStateFlowService;
	private BorrowStateFlowService borrowStateFlowService;
	private LoanItemsChangeStateFlowService loanItemsChangeStateFlowService;
	private LoanStateFlowService loanStateFlowService;
	private AcceptSaleBookService acceptSaleBookService;
	private AcceptRentBookService acceptBookService;
	private TranRentService tranRentService;
	private SearchBookService searchService;
	private BooksBarcodeService booksBarcode;
	private SearchUserService searchUserService;
	private ServiceBase searchService2;
	// private ServiceFireBook svb;
	// private ServiceFireDistance serviceFireDistance;
	private LocationTmpService locationTmpService;
	private ResquesDistanceService resquesServiceFireBook2;
	// private ResquestService resquestSrc;
	// UserChannelCacheService userChannelCacheService;
	MessageService messageService;
	MessageServiceCount messageServiceCount;
	BookSitesService bookSite;
	BookNoa2Service bookNoa2svc;
	private UsersKeyService usersKeyService;

	public void onStart() {

		System.out.println("main execution  start ");
		// Initialize Firebase
		try {
			// [START initialize]

			// usService= new UsersService();

			RatingService RatingSvc = new RatingService();
			RatingSvc.onStart();
			// resquestSrc = new ResquestService();
			// resquestSrc.onStart();

			bookNoa2svc = new BookNoa2Service();
			bookNoa2svc.onStart();

			bookSite = new BookSitesService();
			bookSite.onStart();

			// searchService3 = new KeyBookService();
			// searchService3.onStart();
			//
			// keyUsersService = new KeyUsersService();
			// keyUsersService.onStart();

			usersService = new UsersService();
			usersService.onStart();
			bookService = new BooksItemsNoaService();
			bookService.onStart();
			usersKeyService = new UsersKeyService();
			usersKeyService.onStart();

			// keyAuthorsService = new KeyAuthorService();
			// keyAuthorsService.onStart();

			authorsService = new AuthorService();
			authorsService.onStart();

			// keyCategorysService = new KeyCategoryService();
			// keyCategorysService.onStart();

			categorysService = new CategoryService();
			categorysService.onStart();

			searchCategoryService = new SearchCategoryService();
			searchCategoryService.onStart();
			searchAuthorService = new SearchAuthorService();
			searchAuthorService.onStart();

			searchUserService = new SearchUserService();
			searchUserService.onStart();
			searchService2 = new BookService();
			searchService2.onStart();

			// svb = new ServiceFireBook();
			// svb.onStart();
			// serviceFireDistance = new ServiceFireDistance();
			// serviceFireDistance.onStart ();
			Thread.sleep(5000);

			searchService = new SearchBookService();
			searchService.onStart();
			booksBarcode = new BooksBarcodeService();
			booksBarcode.onStart();
			tranRentService = new TranRentService();
			tranRentService.onStart();
			//
			acceptBookService = new AcceptRentBookService();
			acceptBookService.onStart();

			acceptSaleBookService = new AcceptSaleBookService();
			acceptSaleBookService.onStart();

			loanStateFlowService = new LoanStateFlowService();
			loanStateFlowService.onStart();
			loanItemsChangeStateFlowService = new LoanItemsChangeStateFlowService();
			loanItemsChangeStateFlowService.onStart();
			borrowStateFlowService = new BorrowStateFlowService();
			borrowStateFlowService.onStart();
			borrowItemsChangeStateFlowService = new BorrowItemsChangeStateFlowService();
			borrowItemsChangeStateFlowService.onStart();

			saleStateFlowService = new SeleStateFlowService();
			saleStateFlowService.onStart();
			saleItemsChangeStateFlowService = new SaleItemsChangeStateFlowService();
			saleItemsChangeStateFlowService.onStart();
			PurchaseStateFlowService = new PurchaseStateFlowService();
			PurchaseStateFlowService.onStart();
			PurchaseItemsChangeStateFlowService = new PurchaseItemsChangeStateFlowService();
			PurchaseItemsChangeStateFlowService.onStart();
			FirebaseUsersService = new FirebaseUsersService();
			FirebaseUsersService.onStart();

			locationTmpService = new LocationTmpService();
			locationTmpService.onStart();

			resquesServiceFireBook2 = new ResquesDistanceService();
			resquesServiceFireBook2.onStart();

			// userChannelCacheService = new UserChannelCacheService();
			messageService = new MessageService();
			messageServiceCount = new MessageServiceCount();
			// userChannelCacheService.onStart();
			messageService.onStart();
			messageServiceCount.onStart();

			conversationSearchUserService = new ConversationSearchUserService();
			conversationSearchUserService.onStart();
			SearchBomPhoneNumberService searchBomPhoneNumberService = new SearchBomPhoneNumberService();
			searchBomPhoneNumberService.onStart();

			boomService = new BoomService();
			boomService.onStart();
			
			SearchServiceGennaral searchServiceGennaral = new  SearchServiceGennaral();
			searchServiceGennaral.onStart();
					

		} catch (Exception e) {
			System.out.println("ERROR: invalid service account credentials. See README.");
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}

	private ConversationSearchUserService conversationSearchUserService;
	private BoomService boomService;

	public FirebaseBookServices() {
		// TODO Auto-generated constructor stub
	}

}
