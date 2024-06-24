function telecomSystem(callingTime, count) {
    if (callingTime < 0 || callingTime > 31 * 24 * 60) {
        return "通话时长数值越界";
    }
    if (count < 0 || count > 11) {
        return "未按时缴费次数越界";
    }

    var maxNum = [1, 2, 3, 3, 6];
    var discountRate = [0.01, 0.015, 0.02, 0.025, 0.03];
    var level = getLevel(callingTime);

    if (count <= maxNum[level - 1]) {
        return String(Math.round((25 + 0.15 * callingTime * (1 - discountRate[level - 1]))*100)/100);
    } else {
        return String(Math.round((25 + 0.15 * callingTime) * 100) / 100);
    }
}

function getLevel(time) {
    if (time > 0 && time <= 60) return 1;
    else if (time > 60 && time <= 120) return 2;
    else if (time > 120 && time <= 180) return 3;
    else if (time > 180 && time <= 300) return 4;
    else return 5;
}

// 统一入口函数
function executeTest(args) {
    return telecomSystem.apply(null, args);
}
