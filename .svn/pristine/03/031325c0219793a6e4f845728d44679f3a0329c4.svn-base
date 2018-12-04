import React, { Component } from 'react';
import { connect } from 'dva';
import Table from './components/Table';
import Detail from './components/Detail';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import { Card, Row, Col, Form, Select } from 'antd';
import SelectCustom from './components/Select'

const Option = Select.Option;

@connect(({ history }) => ({
  history
}))

class SalesHistory extends Component {

  constructor(props) {
    super(props);
    const {dispatch, history} = this.props;
    const param = "sum";
    dispatch({
      type: 'history/fetchChange',
      payload: {
        taskType: param
      }
    })
  }

  toDetail = (text, e) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'history/showDetail',
      payload: {
        showDetail: true,
        spCode: text
      }
    });
  };

  goBack = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'history/showDetail',
      payload: {
        showDetail: false,
        spCode: ''
      }
    });
  };

  handleSelectChange = (value) => {
    console.log(value);
    let param = '';
    switch(value){
      case "总销售任务完成度":param = "sum"; break;
      case "广联DVD任务完成度":param = "dvd";break;
      case "广联GPS任务完成度":param = "gps";break;
      case "嘀嘀虎智能云镜任务完成度":param = "yunjing";break;
      case "嘀嘀虎车联网服务激活卡-1年版任务完成度":param = "didihuService";break;
      case "无线车充任务完成度":param = "wirelessCharge";break;
      case "纯流媒体后视镜任务完成度":param = "rearview";break;
      case "全功能_流媒体后视镜任务完成度数":param = "functionRearview";break;
      default:param = "sum"
    }
    const { dispatch } = this.props;
    console.log(param);
    dispatch({
      // type: 'history/selectChange',
      type: 'history/fetchChange',
      payload: {
        taskType: param
      }
    })
  }

  render() {

    const { history } = this.props;
    let view = '';

    if (!history.showDetail) {
      view = (
        <div>

          <Table toDetail={this.toDetail} />
        </div>
      )
    } else {
      view = (
        <div>
          <Detail spCode={history.spCode} goBack={this.goBack} />
        </div>
      );
    }
    // 图表数据
    const chartData = {
      lineData: history.lineData,
      barData1: history.barData1,
      barData2: history.barData2,
      xAxisData: history.xAxisData
    }

    return (
      <PageHeaderWrapper>
        {view}
       <SelectCustom
        data={chartData}
        onchange={this.handleSelectChange}
         />

      </PageHeaderWrapper>
    );
  }
}


export default SalesHistory
