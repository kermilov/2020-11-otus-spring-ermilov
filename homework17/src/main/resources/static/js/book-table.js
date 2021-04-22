function fillForm() {

    $.get('/book').done(function (books) {
        books.forEach(function (book) {
            $('tbody').append(`
                <tr>
                    <td>${book.id}</td>
                    <td>${book.name}</td>
                    <td>${book.author}</td>
                    <td>${book.genres}</td>
                    <td>
                        <a href="/comment?id=${book.id}">Comments</a>
                    </td>
                    <td>
                        <a href="/book/edit?id=${book.id}">Edit</a>
                    </td>
                    <td>
                        <a href="/book/delete?id=${book.id}">Delete</a>
                    </td>
                </tr>
            `)
        });
    })

}
function doAction() {

    window.location.href = '/book/insert';

}