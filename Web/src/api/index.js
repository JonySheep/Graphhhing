// 配置api接口地址
var root = '/'
// 引用axios
var axios = require('axios')

// 参数过滤函数,和自定义判断元素类型函数
function filterNull (o) {
  for (var key in o) {
    if (o[key] === null) {
      delete o[key]
    }
    if (toType(o[key]) === 'string') {
      o[key] = o[key].trim()
    } else if (toType(o[key]) === 'object') {
      o[key] = filterNull(o[key])
    } else if (toType(o[key]) === 'array') {
      o[key] = filterNull(o[key])
    }
  }
  return o
}
function toType (obj) {
  return ({}).toString.call(obj).match(/\s([a-zA-Z]+)/)[1].toLowerCase()
}

// 接口处理函数
function axios_wrapper (method, url, params, success, failure) {
  var axios_data, axios_params;
  if (params) {
    params = filterNull(params);
    if(method === 'POST' || method === 'PUT'){
      axios_data = params;
      axios_params = null;
    }else if(method === 'GET' || method === 'DELETE'){
      axios_data = null;
      axios_params = params;
    }
  }
  axios({
    method: method,
    url: url,
    data: axios_data,
    params: axios_params,
    baseURL: root,
    withCredentials: false
  })
    .then(function (res) {
      if (res.data.success === true) {
        if (success) {
          success(res.data);
        }
      } else {
        if (failure) {
          failure(res.data);
        } else {
          window.alert('error: ' + JSON.stringify(res.data));
        }
      }
    })
    .catch(function (err) {
      let res = err.response;
      if (err) {
        window.alert('api error, HTTP CODE: ' + res.status);
      }
    })

}

// 返回在vue模板中的调用接口
export default {
  get: function (url, params, success, failure) {
    return axios_wrapper('GET', url, params, success, failure)
  },
  post: function (url, params, success, failure) {
    return axios_wrapper('POST', url, params, success, failure)
  },
  put: function (url, params, success, failure) {
    return axios_wrapper('PUT', url, params, success, failure)
  },
  delete: function (url, params, success, failure) {
    return axios_wrapper('DELETE', url, params, success, failure)
  }
}
