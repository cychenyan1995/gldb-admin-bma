import {queryList, queryMonthSalesHistory} from '@/services/history';

export default {
  namespace : 'historyTable',
  state : {
    list: [],
    total: 0,
    filters:{},
    search:{},
    showSearch:false,
    exportMonth:''
  },

  effects : {
    * fetchList({ payload }, {call, put}){
      const response = yield call(queryList,payload);
      yield put({
        type : 'getList',
        payload : response
      })
    },
    * fetchListByMonth({ payload }, {call, put}){
      const response = yield call(queryMonthSalesHistory,payload);
      yield put({
        type : 'getListByMonth',
        payload : response
      })
    }
  },

  reducers : {
    getList(state,action){
      return{
        ...state,
        list: action.payload.data,
        total: action.payload.pageTotal,
        current:action.payload.current,
      }
    },
    getFilters(state,action){
      return {
        ...state,
        filters: action.payload
      }
    },
    search(state, action) {
      return {
        ...state,
        search: action.payload
      };
    },
    showSearch(state, action) {
      return {
        ...state,
        showSearch: action.payload
      };
    },
    setExportMonth(state, action) {
      return {
        ...state,
        exportMonth: state.filters.month,
        list: action.payload.data,
        total: action.payload.pageTotal,
        current:action.payload.current
      };
    },
    getListByMonth(state, action) {
      return {
        ...state,
        list: action.payload.data,
        total: action.payload.pageTotal
      };
    }
  }
}
