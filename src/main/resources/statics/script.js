function confirmDelete() {
    if (confirm("本当に削除してもよろしいですか？")) {
        // 削除処理を実行
        console.log("削除処理が実行されました。");
    } else {
        console.log("削除がキャンセルされました。");
    }
}
