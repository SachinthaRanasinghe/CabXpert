document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");
    const messageBox = document.getElementById("message");

    form.addEventListener("submit", async function (event) {
        event.preventDefault(); // Prevent form from reloading the page

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        const requestData = {
            username: username,
            password: password
        };

        try {
            const response = await fetch("http://localhost:8080/CabXpert_Back-end/webresources/Login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(requestData),
            });

            const data = await response.json();

            if (response.ok) {
                messageBox.innerHTML = `<span style="color: green;">${data.message}</span>`;
                localStorage.setItem("username", username);
                setTimeout(() => {
                    window.location.href = "dashboard.html"; // Redirect after successful login
                }, 1000);
            } else {
                messageBox.innerHTML = `<span style="color: red;">${data.message}</span>`;
            }
        } catch (error) {
            console.error("Error:", error);
            messageBox.innerHTML = `<span style="color: red;">Server not responding.</span>`;
        }
    });
});
