const spawn = require("child_process").spawn;
const superagent = require('superagent');

const pingIntervalMs = 3;

const args = process.argv.slice(2);
if (args.length == 0) {
  console.log('The path of the executable must be specified !');
}

const proc = (args[0].endsWith(".jar") ? spawn("java", ["-jar", args[0]]) : spawn(args[0]));

const startTime = new Date().getTime();
const intervalHandle = setInterval(() => {
	superagent.get('http://127.0.0.1:8080/helloJSON/John').end((error, response) => {
		 if (!error && response?.statusCode === 200) {
          const time = new Date().getTime() - startTime;
          console.log(time + " ms");
          clearInterval(intervalHandle);
          proc.kill();
          process.exit(0);
      } else {
          // @ts-ignore
          if (!error || !error.code === "ECONNREFUSED") {
            console.log(error ? error : response.statusCode);
          }
      }		
	});
}, pingIntervalMs);