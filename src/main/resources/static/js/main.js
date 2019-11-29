$(document).ready(function() {
    feather.replace();
});


function openMovieReviewsDetailsModal(movieReviewId) {
    $.LoadingOverlay("show");
    $.ajax({
        url: "/user/my-reviews/" + movieReviewId,
        success: function (data) {
            $('#movieReviewDetailsModalHolder').html(data);
            $.LoadingOverlay("hide");
            $('#movieReviewDetailsModal').modal('show');
        },
        error: function(data) {
            $.LoadingOverlay("hide");
        }
    })
}