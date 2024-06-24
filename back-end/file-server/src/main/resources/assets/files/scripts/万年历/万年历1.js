function calendarProblem1(year, month, day) {
    // 没判断年份，月份和日期范围

    var monthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var isLeap = 0;
    if (year % 400 == 0) {
        isLeap = 1;
    } else if (year % 100 != 0 && year % 4 == 0) {
        isLeap = 1;
    }

    monthDays[1] += isLeap;
    var maxDays = monthDays[month - 1];
    // 没判断

    var result = [year, month, day + 1];

    if (day == maxDays) {
        result[2] = 1;
        result[1]++;
    }
    // 忘记年份进位
    // if (result[1] > 12) {
    //     result[1] = 1;
    //     result[0]++;
    // }
    return result[0] + "/" + result[1] + "/" + result[2];
}

// 统一入口函数
function executeTest(args) {
    return calendarProblem1.apply(null, args);
}