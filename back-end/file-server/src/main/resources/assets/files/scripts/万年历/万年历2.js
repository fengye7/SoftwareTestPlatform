function calendarProblem2(year, month, day) {
  if (year < 1900 || year > 2100) {
    return "年份数值越界";
  }
  if (month <= 0 || month > 12) {
    return "月份数值越界";
  }

  var monthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  var isLeap = 0;
  if (year % 400 == 0) {
    isLeap = 1;
  } else if (year % 100 != 0 && year % 4 == 0) {
    isLeap = 1;
  }

  monthDays[1] += isLeap;
  var maxDays = monthDays[month - 1];
  if (day <= 0 || day > maxDays) {
    return "日期数值越界";
  }

  var nextYear = year;
  var nextMonth = month;
  var nextDay = day + 1;

  if (nextDay == maxDays + 1) { // 错误：应该是 if (nextDay > maxDays)
    nextDay = 1;
    nextMonth++;
  }
  if (nextMonth == 13) { // 错误：应该是 if (nextMonth > 12)
    nextMonth = 1;
    nextYear++;
  }

  return `${nextYear}/${nextMonth}/${nextDay}`;
}

// 统一入口函数
function executeTest(args) {
  return calendarProblem2.apply(null, args);
}