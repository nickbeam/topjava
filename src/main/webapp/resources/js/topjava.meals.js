// $(document).ready(function () {
let ajaxUrl = "ajax/profile/meals/";

function filter() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filterForm").serialize(),
        success: updateTable
    });
}

function resetFilter() {
    $("#filterForm")[0].reset();
    $.get(ajaxUrl, updateTable);
}

$(function () {
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );
});
