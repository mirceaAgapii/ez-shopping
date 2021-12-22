export interface SystemCPU {
    name: String;
    description: String;
    baseUnit: any;
    measurements: [{
        statistic: String,
        value: number
    }];
    availabale: any[];


}