function openSimCardHistory(simCardId) {
    const url = `/sim-card/history/${simCardId}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            displaySimCardHistory(data);
        })
        .catch(error => console.error('Error:', error));
}

function displaySimCardHistory(data) {
    const modal = document.getElementById('simCardHistoryModal');
    const modalBody = modal.querySelector('.modal-body');
    modalBody.innerHTML = '';
    data.forEach(item => {
        const p = document.createElement('p');
        p.textContent = `Дата: ${item.date}, Описание: ${item.description}`;
        modalBody.appendChild(p);
    });
    modal.style.display = 'block'; // Отображаем модальное окно
}

function closeModal() {
    const modal = document.getElementById('simCardHistoryModal');
    modal.style.display = 'none';
}
