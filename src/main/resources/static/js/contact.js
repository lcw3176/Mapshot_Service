(function () {
    emailjs.init("user_betWihA6XgXwOyOKEHdeq");
})();

window.onload = function () {

    document.getElementById('contact-form').addEventListener('submit', function (event) {
        event.preventDefault();
        if (this.from_name.value == "" && !this.no_reply.checked) {
            alert("답장을 받을 메일을 입력해 주세요.");
            return;
        }

        else if (this.message.value == "") {
            alert("내용을 입력해 주세요");
            return;
        }

        document.getElementById("submitButton").setAttribute("class", "button is-link is-loading");
        this.contact_number.value = Math.random() * 100000 | 0;

        emailjs.sendForm('mapshot_contact', 'template_2wpci79', this)
            .then(function (response) {
                alert("전송 완료 되었습니다.");
                document.getElementById("submitButton").setAttribute("class", "button is-link");

            }, function (error) {
                alert("잠시 후 다시 전송해주세요");
                document.getElementById("submitButton").setAttribute("class", "button is-link");

            });


    });
}
