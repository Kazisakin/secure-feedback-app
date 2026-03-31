document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.toggle-password').forEach(function (button) {
        button.addEventListener('click', function () {
            var input = document.getElementById(button.getAttribute('data-target'));
            var isHidden = input.type === 'password';
            input.type = isHidden ? 'text' : 'password';
            button.querySelector('.eye-open').style.display = isHidden ? 'none' : 'block';
            button.querySelector('.eye-closed').style.display = isHidden ? 'block' : 'none';
        });
    });
});
