document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.filter-btn').forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.querySelectorAll('.filter-btn').forEach(function (b) { b.classList.remove('active'); });
            btn.classList.add('active');

            var filter = btn.getAttribute('data-filter');
            document.querySelectorAll('tbody tr').forEach(function (row) {
                var account = row.getAttribute('data-account');
                row.style.display = (filter === 'all' || account === filter) ? '' : 'none';
            });
        });
    });
});
