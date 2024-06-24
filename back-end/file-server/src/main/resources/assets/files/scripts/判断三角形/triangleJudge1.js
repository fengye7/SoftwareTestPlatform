function wrongTriangleJudge(a, b, c) {
    // 边界范围有误
    if (a < 0 || b < 0 || c < 0 || a > 200 || b > 200 || c > 200) {
        return '边长数值越界';
    }
    // 没判断不构成三角形的情况
    if (a === b && a === c) {
        return '该三角形是等边三角形';
    } else if (a === b || b === c || a === c) {
        return '该三角形是等腰三角形';
    } else {
        return '该三角形是普通三角形';
    }
}

// 统一入口函数
function executeTest(args) {
    return wrongTriangleJudge.apply(null, args);
}