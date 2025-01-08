document.addEventListener("DOMContentLoaded", function () {
    // 모달창 닫기를 위한 코드
    document.querySelectorAll('.cancelBtn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const modal = e.target.closest('.modalBody').parentNode;
            modal.style.display = "none";
            document.body.style.overflow = "";
        });
    });

    const employeeColorDivs = document.querySelectorAll('.employeeColor');

    // 직원 분류에 따른 값의 추가
    employeeColorDivs.forEach(function (div) {
        const classifyStyle = div.querySelector('.classifyStyle').textContent.toLowerCase();
        let color;

        if (classifyStyle === '배송집화기사') {
            color = 'linear-gradient(to right, #C8E6D2, #f7fffa)';
        } else if (classifyStyle === '배송기사') {
            color = 'linear-gradient(to right, #C7D8E8, #f2faff)';
        } else if (classifyStyle === '집화기사') {
            color = 'linear-gradient(to right, #FAF1D6, #fffcf5)';
        } else {
            console.error('Unknown classifyStyle value:', classifyStyle);
        }

        if (color) {
            div.style.background = color;
        }
    });

    // 직원 카드에 해당 월을 표기하기 위한 처리
    const closeMonths = document.getElementsByClassName('closeOne');
    const today = new Date();
    let month = today.getMonth() + 1;
    const monthString = month.toString();
    for (let i = 0; i < closeMonths.length; i++) {
        closeMonths[i].textContent = monthString;
    }

    // 직원 수정을 위한 선처리
    const updateButtons = document.querySelectorAll('.employeeUpdateBtn');
    // 직원 수정 모달창을 열기 위한 코드
    const updateModal = document.querySelector('.updateModal');

    updateButtons.forEach(button => {
        button.addEventListener('click', function () {
            const employeeCard = button.closest('.employeeCard');
            if (employeeCard) {
                const employeeIdValue = employeeCard.querySelector('.employeeIdValue');
                const employeeId = employeeIdValue.value;
                const employeeClassifyValue = employeeCard.querySelector('.classifyStyle');
                const employeeClassify = employeeClassifyValue.textContent;
                const employeeNameValue = employeeCard.querySelector('.employeeName');
                const employeeName = employeeNameValue.textContent;
                const employeeJoinValue = employeeCard.querySelector('.employeeJoin');
                const employeeJoin = employeeJoinValue.textContent;
                const buttons = document.querySelectorAll('.classifyBtnGroup button');
                document.getElementById('updateEmployeeId').value = employeeId;
                document.getElementById('updateName').value = employeeName;
                document.getElementById('updateJoinDate').value = employeeJoin;

                buttons.forEach(button => {
                    button.classList.remove('selected')
                });

                buttons.forEach(button => {
                    if (button.textContent.trim() === employeeClassify) {
                        button.classList.add('selected');
                        document.getElementById('selectedButtonValueTwo').value = employeeClassify;
                    }
                });
            } else {
                console.log('직원 카드가 존재하지 않음');
            }
            // 페이지 맨 위로 스크롤하고 스크롤을 막기 위한 코드
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
            setTimeout(function () {
                updateModal.style.display = "flex";
                document.body.style.overflow = "hidden";
            }, 400);
        });
    });
    // 삭제 확인을 위한 코드
    const deleteButtons = document.querySelectorAll('.employeeDeleteBtn');
    deleteButtons.forEach(function (button) {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            if (confirm("정말 삭제하시겠습니까?")) {
                button.closest('form').submit();
            } else {
                console.log('삭제가 취소되었습니다.');
            }
        });
    });
});
// 직원 등록 모달창을 열기 위한 코드
const writeModal = document.querySelector('.writeModal');
const writeModalCloseOpen = document.querySelector('.employeeWriteBtn');

writeModalCloseOpen.addEventListener("click", () => {
    writeModal.style.display = "flex";
    document.body.style.overflow = "hidden";
    // 직원 등록 모달창이 열릴 때 기본 선택된 버튼 확인 및 설정
    const defaultButton = document.querySelector('.classifyBtnGroup button.selected');
    if (defaultButton) {
        document.getElementById('selectedButtonValue').value = defaultButton.textContent;
    }
});

// 직원 분류 버튼의 처리를 위한 코드
function selectButton(button, value) {
    // 모든 분류 버튼의 'selected' 클래스 제거
    const buttons = document.querySelectorAll('.classifyBtnGroup button');
    buttons.forEach(btn => btn.classList.remove('selected'));

    // 클릭된 분류 버튼에 'selected' 클래스 추가
    button.classList.add('selected');

    // 숨겨진 입력 필드에 선택된 분류 버튼의 값 설정
    document.getElementById('selectedButtonValue').value = value;
    document.getElementById('selectedButtonValueTwo').value = value;
}

// 직원 검색 모달창을 열기 위한 코드
const searchModal = document.querySelector('.searchModal');
const searchModalCloseOpen = document.querySelector('.employeeSearch');

searchModalCloseOpen.addEventListener("click", () => {
    searchModal.style.display = "flex";
    document.body.style.overflow = "hidden";
});
// 검색 처리를 위한 코드
const modalSearchBtn = document.getElementById('modalSearchBtn');
modalSearchBtn.addEventListener("click", () => {
    const searchModalValue = document.getElementById('searchModal');
    const searchInput = searchModalValue.querySelector('input[name="search"]')
    const searchValue = searchInput.value;
    if (searchValue) {
        document.location.href = '/employee/list?search=' + searchValue;
    }
});