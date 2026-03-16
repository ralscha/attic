var span = document.querySelector('#time-now');

export default function update() {
	span.textContent = new Date().toString();
	setTimeout(update, 1000);
}