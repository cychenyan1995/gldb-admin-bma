import React, { Component } from 'react';
import { Row, Col, Button, Form, Upload, Icon, message} from 'antd';
import { connect } from 'dva/index';

@connect(({ salesTask }) => ({
  salesTask,// namespace action
}))
class ImportModal extends Component {

  constructor(props){
    super(props);
    this.state = {
    }
  }

  normFile = (e) => {
    console.log('Upload event:', e);
    if (Array.isArray(e)) {
      return e;
    }
    return e && e.fileList;
  };

  importFile = (file) => {
    const {dispatch} = this.props;
    dispatch({
      type: 'salesTask/fetchImportTask',
      payload: file,
    });
  };

  render() {
    const FormItem = Form.Item;

    const formItemLayout = {
      labelCol: { span: 5 },
      wrapperCol: { span: 14 },
    };

    const { form } = this.props;
    const { getFieldDecorator } = form;
    // const {dispatch} = this.props;
    const upload = {
      name: 'file',
      action: 'http://localhost:8088/gldb-admin-bma/salesTask/importSalesTask',
      headers: {
        authorization: 'authorization-text',
      },
      onChange(info) {
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          /* dispatch({
            type: 'salesTask/fetchImportTask',
            payload: info.file.originFileObj
          }); */
          message.success(`${info.file.name} 文件上传成功`);
        } else if (info.file.status === 'error') {
          message.error(`${info.file.name} 文件上传失败.`);
        }
      },
    };

    return (

      <Form className="ant-advanced-search-form">
        <Row>
          <Col span={24}>
            <FormItem label="导入" {... formItemLayout}>
              {getFieldDecorator('upload',{
                valuePropName: 'fileList',
                getValueFromEvent: this.normFile,
              }, {
                rules: [
                  { required: true, message: '请选择上传文件!' },
                ],
              })(
                <Upload {...upload} >
                  <Button style={{marginLeft:'20px'}}>
                    <Icon type="upload" /> 选择文件
                  </Button>
                </Upload>
              )}
            </FormItem>
          </Col>
        </Row>
      </Form>
    )
  }
}

export default Form.create()(ImportModal)
