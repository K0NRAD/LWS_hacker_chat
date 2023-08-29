// JavaScript-API-Service
export default class TaskApiService {
    static baseUrl = 'http://localhost:8080/tasks';

    static async getTasks() {
        const response = await fetch(this.baseUrl);
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('Fehler beim Abrufen der Tasks');
        }
    }

    static async postTask(task) {
        const response = await fetch(this.baseUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(task),
        });
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error('Fehler beim Erstellen des Tasks');
        }
    }

    static async deleteTaskById(id) {
        const response = await fetch(`${this.baseUrl}?id=${id}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error('Fehler beim Löschen des Tasks');
        }
    }
}
