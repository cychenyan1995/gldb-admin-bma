import React, { Component } from 'react';
import { Card, Table, Button, Icon } from 'antd';

class Detail extends Component {

  constructor(props) {
    super(props);
    this.state = {
      addColumns: [
        { title: '销售数', width: 80, dataIndex: 'saleNum' },
        { title: '任务完成率', width: 80, dataIndex: 'ratio' },
      ]
    }
  }

    setTitle = () => {
      let desc = this.props.title;
      let myDate = new Date();
      let time = myDate.getFullYear() + '年' + myDate.getMonth();
      return desc + time + '销售任务报表'
    }

    render() {
      /*    const dataSource = [
            { manager: '广联DVD', taskNum: 50, saleNum: 30, ratio: '60%' }
          ]*/
      const dataSource = this.props.dataSource;
      const taskType = this.props.taskType;

      let columns = [{
          title: '任务类型',
          width: 100,
          dataIndex: 'taskType',
        },
        { title: '任务数', width: 80, dataIndex: 'taskNum' },
 /*       { title: '销售数', width: 80, dataIndex: 'saleNum' },
        { title: '任务完成率', width: 80, dataIndex: 'ratio' },*/
      ];

/* taskType === 0  显示完成度   taskType === 1  不显示完成度*/
      if(taskType === '0') {
        columns = columns.concat(this.state.addColumns);
      }

      return (
        <div>
        <Button onClick={this.props.goBack}>
          <Icon type="left-circle" style={{fontSize:'20px'}}>返回</Icon>
        </Button>
          <Table style={{textAlign: 'center'}} bordered={false}
            columns={columns}
            dataSource={dataSource}
            bordered
            title={this.setTitle}
            pagination={false}
          />
      </div>
      )
    }
  }

  export default Detail
