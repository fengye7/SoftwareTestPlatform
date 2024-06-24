// 接收一组参数，返回字符串结果
function computerSelling(host, monitor, peripheral) {
  if (host <= 0 || monitor <= 0 || peripheral <= 0) {
    return "数据非法，各部件销售数量不能小于1";
  }
  if (host > 70) {
    return "数据非法，主机销售数量不能超过70";
  }
  if (monitor > 80) {
    return "数据非法，显示器销售数量不能超过80";
  }
  if (peripheral > 90) {
    return "数据非法，外设销售数量不能超过90";
  }
  // 忘记了统计月度销售额模式

  var totalSales = host * 25 + monitor * 30 + peripheral * 45;
  if (totalSales <= 1000) {
    return String(totalSales * 0.1);
  } else if (totalSales <= 1800) {
    return String(totalSales * 0.15);
  } else {
    return String(totalSales * 0.2);
  }
}

// 统一入口函数
function executeTest(args) {
  return computerSelling.apply(null, args);
}
