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

function initializeUserMovieReviewsTableStarRatings() {
    $(".movie-review-row").each(function(index) {

        const movieReviewId = $(this).data('movie-review-id');
        const userMovieReviewRating = $(this).data('user-movie-review-rating');
        $("#user-movie-review-rating-table-" + movieReviewId).rateYo({
            rating: userMovieReviewRating,
            readOnly: true
        });
    });
}

function initializeUserMovieReviewDetailsModal() {
    $(function () {
        const movieReviewId = $('#movieReviewDetailsModal').data('movie-review-id');
        const movieRating = $('#movieReviewDetailsModal').data('user-movie-rating');
        $("#user-movie-review-rating-modal-" + movieReviewId).rateYo({
            rating: movieRating,
            readOnly: true
        });
    });
}