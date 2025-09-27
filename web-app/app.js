let size = 5;
let playerColor = "w";
let gameOver = false;
const boardDiv = document.getElementById("board");
boardDiv.style.gridTemplateColumns = `repeat(${size}, 60px)`;

let board = Array(size).fill().map(() => Array(size).fill(" "));

function renderBoard() {
    boardDiv.innerHTML = "";
    for (let y = 0; y < size; y++) {
        for (let x = 0; x < size; x++) {
            const cell = document.createElement("div");
            cell.className = "cell";
            if (board[y][x] === "w") cell.classList.add("w");
            if (board[y][x] === "b") cell.classList.add("b");
            cell.addEventListener("click", () => handlePlayerMove(x, y));
            boardDiv.appendChild(cell);
        }
    }
}

function handlePlayerMove(x, y) {
    if (gameOver) return;
    if (board[y][x] !== " ") return;

    board[y][x] = playerColor;
    renderBoard();
    sendToServer();
}

function sendToServer() {
    const flat = board.flat().join("");

    const nextColor = (playerColor === "w") ? "b" : "w";

    const dto = {
        size: size,
        data: flat,
        nextPlayerColor: nextColor
    };

    fetch("http://localhost:8080/api/any/nextMove", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dto)
    })
        .then(async res => {
            const data = await res.json();

            if (res.ok) {
                if (data.x !== undefined && data.y !== undefined) {
                    board[data.y][data.x] = data.color;
                    renderBoard();
                }
            } else {
                const statusEl = document.getElementById("status");
                if (res.status === 400 && data.result !== undefined) {
                    statusEl.textContent = `Игра закончена! ${data.result}`;
                    gameOver = true;

                    if (data.winPoints) {
                        data.winPoints.forEach(pt => {
                            const index = pt.y * size + pt.x;
                            const cell = boardDiv.children[index];
                            cell.classList.add("highlight");
                        });
                    }
                } else if (res.status === 409) {
                    statusEl.textContent = `Нет доступных ходов: ${data.message}`;
                } else {
                    statusEl.textContent = `Ошибка: ${data.message || "Неизвестная ошибка"}`;
                }
                statusEl.classList.add("show");
            }
        })
        .catch(err => {
            console.error(err);
            document.getElementById("status").textContent = "Ошибка связи с сервером";
            document.getElementById("status").classList.add("show");
        });
}

document.getElementById("restartBtn").addEventListener("click", () => {
    board = Array(size).fill().map(() => Array(size).fill(" "));
    gameOver = false;
    renderBoard();
    const statusEl = document.getElementById("status");
    statusEl.textContent = "";
    statusEl.classList.remove("show");
});

document.querySelectorAll("input[name=playerColor]").forEach(radio => {
    radio.addEventListener("change", e => {
        playerColor = e.target.value;

        board = Array(size).fill().map(() => Array(size).fill(" "));
        gameOver = false;
        renderBoard();

        const statusEl = document.getElementById("status");
        statusEl.textContent = "";
        statusEl.classList.remove("show");
    });
});

document.getElementById("applySizeBtn").addEventListener("click", () => {
    size = parseInt(document.getElementById("boardSize").value, 10);
    board = Array(size).fill().map(() => Array(size).fill(" "));
    boardDiv.style.gridTemplateColumns = `repeat(${size}, 60px)`;
    gameOver = false;
    renderBoard();

    const statusEl = document.getElementById("status");
    statusEl.textContent = "";
    statusEl.classList.remove("show");
});

renderBoard();
