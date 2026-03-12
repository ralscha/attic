import http from 'k6/http';
import { check } from 'k6';

export default function () {
  const res = http.get('http://localhost:8080/mandelbrot?iterations=2000&width=200&height=100');
  check(res, { 'status was 200': (r) => r.status == 200 });
}
