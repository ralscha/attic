import http from "k6/http";
import { check } from "k6";

export const options = {
    stages: [
        // Linearly ramp up from 1 to 1000 VUs during first minute
        { target: 1000, duration: "1m" },
        // Hold at 1000 VUs for the next 3 minutes and 30 seconds
        { target: 1000, duration: "3m30s" },
        // Linearly ramp down from 1000 to 0 VUs over the last 30 seconds
        { target: 0, duration: "30s" }
        // Total execution time will be ~5 minutes
    ]
};

export default function() {
    const response = http.get("http://127.0.0.1:8080/helloJSON/John");
		
    const checkRes = check(response, {
        "status is 200": (r) => r.status === 200,
        "content is present": (r) => r.body.indexOf("{\"msg\":\"Hello John\",\"ts\"") !== -1,
    });
};