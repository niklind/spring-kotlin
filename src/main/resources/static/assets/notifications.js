// if (Notification.permission === "granted") {
    Notification.requestPermission().then(function(result) {
        console.log(result);
    });
// }

let eventSource = new EventSource("/api/movie/notifications");
eventSource.addEventListener("message", function(e) {
    let movie = JSON.parse(e.data);
    let notification = new Notification(movie.title);
    notification.onclick = function() {
        window.location.href = "/" + movie.url;
    };
});
