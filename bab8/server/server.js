const express = require('express');
const fs = require('fs');
const app = express();
const port = 3000;

app.use(express.json());

const DB_FILE = './students.json';

// =======================
// Helper function
// =======================
function readData() {
    const data = fs.readFileSync(DB_FILE, 'utf-8');
    return JSON.parse(data);
}

function writeData(data) {
    fs.writeFileSync(DB_FILE, JSON.stringify(data, null, 2));
}

// =======================
// GET (READ)
// =======================
app.get('/api/students', (req, res) => {
    const students = readData();
    res.json({
        message: 'Data mahasiswa',
        data: students
    });
});

// =======================
// POST (CREATE)
// =======================
app.post('/api/students', (req, res) => {
    const students = readData();
    const { name, age, major } = req.body;

    if (!name || !age || !major) {
        return res.status(400).json({
            message: 'Name, age, and major are required'
        });
    }

    const newStudent = {
        id: students.length > 0 ? students[students.length - 1].id + 1 : 1,
        name,
        age,
        major
    };

    students.push(newStudent);
    writeData(students);

    res.json({
        message: 'Data berhasil ditambahkan',
        data: students
    });
});

// =======================
// PUT (UPDATE)
// =======================
app.put('/api/students/:id', (req, res) => {
    const students = readData();
    const id = parseInt(req.params.id);
    const { name, age, major } = req.body;

    const index = students.findIndex(s => s.id === id);

    if (index === -1) {
        return res.status(404).json({
            message: 'Data tidak ditemukan'
        });
    }

    students[index] = { id, name, age, major };
    writeData(students);

    res.json({
        message: 'Data berhasil diupdate',
        data: students
    });
});

// =======================
// DELETE
// =======================
app.delete('/api/students/:id', (req, res) => {
    const students = readData();
    const id = parseInt(req.params.id);

    const newData = students.filter(s => s.id !== id);
    writeData(newData);

    res.json({
        message: 'Data berhasil dihapus',
        data: newData
    });
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
