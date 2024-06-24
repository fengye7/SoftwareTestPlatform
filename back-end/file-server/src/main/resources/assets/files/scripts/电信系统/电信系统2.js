// 接收一组参数，返回字符串结果
function telecomSystem(callingTime, count) {
    if (callingTime < 0 || callingTime > 31 * 24 * 60) {
        return "通话时长数值越界";
    }
    if (count < 0 || count > 11) {
        return "未按时缴费次数越界";
    }

    var maxNum = [1, 2, 3, 3, 6];
    var level = getLevel(callingTime);
    if (count <= maxNum[level - 1]) {
        return String((25 + 0.15 * callingTime * (1 - (level + 1) * 0.005)));
    } else {
        return String((25 + 0.15 * callingTime));
    }
}

// 获取折扣档位
function getLevel(time) {
    if (time > 0 && time <= 60)
        return 1;
    else if (time > 60 && time <= 120)
        return 2;
    else if (time > 120 && time <= 180)
        return 3;
    else if (time > 180 && time <= 300)
        return 4;
    else
        return 5;
}

// 统一入口函数
function executeTest(args) {
    return telecomSystem.apply(null, args);
}
