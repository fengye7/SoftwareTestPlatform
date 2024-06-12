function calendarProblem3(year, month, day) {
  var isLeapYear = (year) => {
    return (year % 400 === 0) || (year % 100 !== 0 && year % 4 === 0);
  };

  if (year < 1900 || year > 2100) {
    return "年份数值越界";
  }
  if (month < 1 || month > 12) {
    return "月份数值越界";
  }

  var monthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  if (isLeapYear(year)) {
    monthDays[1] = 29;
  }

  var maxDays = monthDays[month - 1];
  if (day < 1 || day > maxDays) {
    return "日期数值越界";
  }

  var nextYear = year;
  var nextMonth = month;
  var nextDay = day + 1;

  if (nextDay > maxDays) {
    nextDay = 1;
    nextMonth++;
  }
  if (nextMonth > 12) {
    nextMonth = 1;
    nextYear++;
  }

  return `${nextYear}/${nextMonth}/${nextDay}`;
}

// 统一入口函数
function executeTest(args) {
  return calendarProblem3.apply(null, args);
}