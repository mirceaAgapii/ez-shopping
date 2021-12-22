export interface SystemlHealth {
    status: String;
    components: {
        db: {
            status: String,
            details: {
                database: String,
                validationQuery: String
            }
        },
        diskSpace: {
            status: String,
            details: {
                total: number,
                free: number | string,
                threshold: number,
                exists:String
            }
        },
        ping:{
            status:String
        }

    };


}