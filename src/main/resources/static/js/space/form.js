$(function() {
    let max_file_size_btyes = 10 * 1024 * 1024;
    let validFiles = [];

    // 파일 선택
    $('#imageSelectBtn').on('click', function() {
        document.getElementById('images').click();
    })

    $('#images').on('change', function() {
        let files = this.files;
        let errors = [];

        for(let i = 0; i< files.length; i++) {
            let file = files[i];
            if(!file.type.startsWith('image/')) {
                errors.push(file.name + "은 이미지 파일이 아닙니다.");
                continue;
            }
            if(file.size > max_file_size_btyes) {
                error.push(file.name + "의 크기가 10MB를 초과했습니다.");
                continue;
            }
            validFiles.push(file);
        }

        $('#imageFileCount').text(
            validFiles.length > 0 ? validFiles.length + '개 파일 선택됨' : '선택된 파일 없음'
        )

    })
})