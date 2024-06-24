function triangleJudge(a, b, c) {
    // 边界范围正确
    if (a <= 0 || b <= 0 || c <= 0 || a > 200 || b > 200 || c > 200) {
        return '边长数值越界';
    }
    // 正确判断了是否构成三角形
    if (a + b > c && a + c > b && b + c > a) {
        if (a === b && b === c) {
            return '该三角形是等边三角形';
        } else if (a === b || a === c || b === c) {
            return '该三角形是等腰三角形';
        } else {
            return '该三角形是普通三角形';
        }
    } else {
        return '所给三边数据不能构成三角形';
    }
}

// 统一入口函数
function executeTest(args) {
    return triangleJudge.apply(null, args);
}