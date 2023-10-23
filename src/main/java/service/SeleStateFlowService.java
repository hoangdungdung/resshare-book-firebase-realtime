package service;

import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.resshare.book.RefFireBaseBook;

import model.StateFlow;
import service.cache.Cache;
import service.cache.CacheServiceBase;

public class SeleStateFlowService extends CacheServiceBase{

	@Override
	public Map getCacheObject() {
		// TODO Auto-generated method stub
		return Cache.mSaleStateFlow;
	}

	@Override
	public Class getClassT() {
		// TODO Auto-generated method stub
		return StateFlow.class;
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.SAlE_STATE_FLOW;
	} }
