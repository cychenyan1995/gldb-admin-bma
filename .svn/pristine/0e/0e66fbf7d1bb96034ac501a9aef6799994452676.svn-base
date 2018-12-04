import {queryChartList, queryChartListByType} from '@/services/history';

export default {
  namespace: 'history',

  state: {
    showDetail: false,
    spCode: '',
    lineData: [],
    barData1: [],
    barData2: [],
    xAxisData: []
  },
  effects: {
    * fetchChart({payload}, {call, put}) {
      const response = yield call(queryChartList, payload);
      yield put({
        type: 'getChart',
        payload: response,
      });
    },
    * fetchChange({payload}, {call, put}) {
      const response = yield call(queryChartListByType, payload);
      yield put({
        type: 'getChange',
        payload: response,
      });
    },
  },
  reducers: {
    showDetail(state, action) {
      return {
        ...state,
        showDetail: action.payload.showDetail,
        spCode: action.payload.spCode
      };
    },

    selectChange(state, action) {
      return {
        ...state,
        lineData: [1, 13, 33, 35, 15, 55, 25, 21, 6, 45],
        barData1: [22, 22, 23, 88, 24, 55, 55, 89, 98, 164],
        barData2: [201, 222, 223, 777, 99, 255, 555, 879, 938, 1364],
      }
    },
    getChart(state,action){
      return{
        ...state,
        lineData: action.payload.data.sumRateList,
        barData1: action.payload.data.sumSaleList,
        barData2: action.payload.data.sumTaskList,
        xAxisData:action.payload.data.monthList,
      }
    },
    getChange(state,action){
      return{
        ...state,
        lineData: action.payload.data.rateList,
        barData1: action.payload.data.saleList,
        barData2: action.payload.data.taskList,
        xAxisData:action.payload.data.monthList,
      }
    }
  },
};
