document.addEventListener('DOMContentLoaded', function () {
    var passwordInput = document.getElementById('password');
    var strengthBar = document.getElementById('strengthBar');
    var strengthLabel = document.getElementById('strengthLabel');
    var submitBtn = document.querySelector('button[type="submit"]');

    if (!passwordInput || !strengthBar || !strengthLabel) return;

    var levels = [
        { label: '',       color: 'transparent',           width: '0%'   },
        { label: 'Weak',   color: 'rgba(255,100,100,0.8)', width: '25%'  },
        { label: 'Fair',   color: 'rgba(255,180,50,0.8)',  width: '50%'  },
        { label: 'Good',   color: 'rgba(200,220,80,0.8)',  width: '75%'  },
        { label: 'Strong', color: 'rgba(100,220,120,0.8)', width: '100%' }
    ];

    passwordInput.addEventListener('input', function () {
        var val = passwordInput.value;
        var score = 0;
        if (val.length >= 12)         score++;
        if (/[A-Z]/.test(val))        score++;
        if (/[a-z]/.test(val))        score++;
        if (/[0-9]/.test(val))        score++;
        if (/[^a-zA-Z0-9]/.test(val)) score++;

        var level = score === 0 ? 0 : score <= 2 ? 1 : score === 3 ? 2 : score === 4 ? 3 : 4;
        var current = levels[level];

        strengthBar.style.width = current.width;
        strengthBar.style.backgroundColor = current.color;
        strengthLabel.textContent = current.label;
        strengthLabel.style.color = current.color;

        if (submitBtn) {
            submitBtn.disabled = (level < 4);
        }
    });
});
