export default class TaskListApi {
    static async save(tasks) {
        localStorage.setItem('tasks', JSON.stringify(tasks));
    }

    static async load() {
        return JSON.parse(localStorage.getItem('tasks') || '[]');
    }
}