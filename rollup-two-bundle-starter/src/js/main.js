import {dep1} from './dep-1.js';
import {dep2} from './dep-2.js';

const main = async () => {
  console.log('Dependency 1 value:', dep1);
  console.log('Dependency 2 value:', dep2);
  
  const outputElement = document.getElementById('output');

  outputElement.innerText = 'Fetching data, awaiting response...';
  const response = await fetch('https://httpbin.org/user-agent');
  outputElement.innerText = await response.text();
};

main();
