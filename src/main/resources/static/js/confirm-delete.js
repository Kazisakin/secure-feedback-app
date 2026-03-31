document.addEventListener('DOMContentLoaded', function () {
    var overlay = document.getElementById('confirmOverlay');
    var confirmUsername = document.getElementById('confirmUsername');
    var confirmForm = document.getElementById('confirmForm');
    var cancelBtn = document.getElementById('confirmCancel');

    document.querySelectorAll('.delete-btn').forEach(function (btn) {
        btn.addEventListener('click', function () {
            confirmUsername.textContent = btn.getAttribute('data-username');
            confirmForm.action = btn.getAttribute('data-action');
            overlay.classList.add('show');
        });
    });

    cancelBtn.addEventListener('click', function () {
        overlay.classList.remove('show');
    });

    overlay.addEventListener('click', function (e) {
        if (e.target === overlay) {
            overlay.classList.remove('show');
        }
    });
});
