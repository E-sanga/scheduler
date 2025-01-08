document.addEventListener("DOMContentLoaded", function () {
    // 모달창 닫기를 위한 코드
    document.querySelectorAll('.cancelBtn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const modal = e.target.closest('.modalBody').parentNode;
            modal.style.display = "none";
            document.body.style.overflow = "";
            document.location.href = '/team/list';
        });
    });
    // 팀 수정을 위한 선처리
    const updateButtons = document.querySelectorAll('.teamUpdateBtn');
    // 팀 수정 모달창을 열기 위한 코드
    const updateModal = document.querySelector('.updateModal');

    updateButtons.forEach(button => {
        button.addEventListener('click', function () {
            const teamCard = button.closest('.teamCard');
            if (teamCard) {
                const teamNameValue = teamCard.querySelector('.teamName');
                const teamName = teamNameValue.textContent;
                const teamIdValue = teamCard.querySelector('.teamIdValue');
                const teamId = teamIdValue.value;
                const teamLeaderNameValue = teamCard.querySelector('.teamLeader');
                document.getElementById('teamUpdateName').value = teamName;
                document.getElementById('updateTeamId').value = teamId;
                // 팀장이 팀에 존재한다면 실행되는 코드
                if (teamLeaderNameValue) {
                    document.getElementById('updateLeader').textContent = teamLeaderNameValue.textContent;
                    const leaderIdValue = teamCard.querySelector('.leaderId');
                    const leaderId = leaderIdValue.textContent;
                    const leaderChangeBtn = document.getElementById('leaderChangeBtn');
                    leaderChangeBtn.setAttribute('data-mid', leaderId);
                    leaderChangeBtn.setAttribute('data-mrole', '팀장');
                } else {
                    const updateLeaderNone = document.getElementById('updateLeaderDiv');
                    const updateSelectLeader = document.getElementById('updateSelectLeaderDiv');
                    updateLeaderNone.style.display = 'none';
                    updateSelectLeader.style.display = 'flex';
                    // 팀장 선택을 위한 코드
                    const memberInfos = teamCard.querySelector('.teamMemberRow');
                    const memberInfo = memberInfos.getElementsByClassName('memberInfo');
                    const leaderOption = document.getElementById('updateSelectLeader');
                    for (const info of memberInfo) {
                        const memberNameInfoValue = info.querySelector('.teamMember');
                        const memberNameInfo = memberNameInfoValue.textContent;
                        const memberIdInfoValue = info.querySelector('.memberId');
                        const memberIdInfo = memberIdInfoValue.textContent;
                        // 팀장 선택을 위한 옵션 만들기
                        const option = document.createElement('option');
                        option.value = memberIdInfo;
                        option.textContent = memberNameInfo;
                        leaderOption.appendChild(option);
                    }
                }
                // 여기에 만들것은 팀원 목록
                const memberListInfos = teamCard.querySelector('.teamMemberRow');
                const memberListInfo = memberListInfos.getElementsByClassName('memberInfo');
                const membersInfo = document.getElementById('teamMemberList');
                // 중복 이벤트 방지를 위한 기존 내용 삭제
                //membersInfo.innerHTML = '';
                if (memberListInfo.length > 0) {
                    for (const info of memberListInfo) {
                        const memberNameInfoValue = info.querySelector('.teamMember');
                        const memberNameInfo = memberNameInfoValue.textContent;
                        const memberIdInfoValue = info.querySelector('.memberId');
                        const memberIdInfo = memberIdInfoValue.textContent;
                        // 팀원 삭제를 위한 팀원 나열
                        const teamMembers = document.createElement('div');
                        teamMembers.className = "deleteTeamMemberDiv";
                        // span 태그 생성 후 팀원 이름 세팅
                        const memberSpanTag = document.createElement('span');
                        memberSpanTag.textContent = memberNameInfo;
                        teamMembers.appendChild(memberSpanTag);
                        // 삭제를 위한 버튼 생성
                        const memberDeleteBtn = document.createElement('button');
                        memberDeleteBtn.setAttribute('data-mid', memberIdInfo);
                        memberDeleteBtn.type = "button";
                        memberDeleteBtn.textContent = "삭제";
                        teamMembers.appendChild(memberDeleteBtn);
                        membersInfo.appendChild(teamMembers);
                    }
                } else {
                    const teamMembers = document.createElement('div');
                    teamMembers.id = "nonMemberDiv";
                    const noneMemberSpanTag = document.createElement('span');
                    noneMemberSpanTag.textContent = "팀원이 존재하지 않습니다.";
                    noneMemberSpanTag.className = "noneMemberSpan";
                    teamMembers.appendChild(noneMemberSpanTag);
                    membersInfo.appendChild(teamMembers);
                }
                // 팀원 추가를 위한 선처리
                const memberAddBtn = document.getElementById('memberAddBtn');
                const memberAddDiv = document.getElementById('memberAddDiv');
                memberAddBtn.addEventListener('click', function () {
                    memberAddDiv.style.display = 'flex';
                });
                // 팀원 추가 취소 처리
                const memberCrateCancelBtn = document.getElementById('memberCrateCancelBtn');
                memberCrateCancelBtn.addEventListener('click', function () {
                    memberAddDiv.style.display = 'none';
                });
                // 팀원 추가를 위한 코드
                const memberCrateBtn = document.getElementById('memberCrateBtn');
                // 완료 버튼 클릭 시 비동기 통신 및 memberAddDiv 숨기기
                memberCrateBtn.addEventListener('click', function () {
                    const select = document.getElementById('member');
                    const selectedOption = select.options[select.selectedIndex].value;
                    const [memberName, employeeId] = selectedOption.split('|');
                    // 멤버 추가 비동기 통신
                    fetch('/member/write', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            memberName: memberName,
                            employeeId: employeeId,
                            teamId: teamId
                        })
                    })
                        .then(response => response.json())
                        .then(data => {
                            console.log('Success:', data);
                            // 팀원이 아무도 없었을때 보여지던 div를 삭제 처리
                            const nonMemberDiv = document.getElementById('nonMemberDiv');
                            if (nonMemberDiv) {
                                nonMemberDiv.remove();
                            }
                            memberAddDiv.style.display = 'none';
                            // 팀원 삭제를 위한 팀원 나열
                            const teamMembers = document.createElement('div');
                            teamMembers.className = "deleteTeamMemberDiv";
                            // span 태그 생성 후 팀원 이름 세팅
                            const memberSpanTag = document.createElement('span');
                            memberSpanTag.textContent = memberName;
                            teamMembers.appendChild(memberSpanTag);
                            membersInfo.appendChild(teamMembers);
                            // 선택된 select-option 요소 삭제
                            select.options[select.selectedIndex].remove();
                        })
                        .catch((error) => {
                            console.error('Error:', error);
                            alert('추가 실패!');
                        });
                });
                // 팀장 변경과 팀원 삭제를 위한 코드
                const deleteMember = (mid, mrole) => {
                    const mLeader = mrole ? 1 : 0;
                    console.log(mLeader);
                    const url = '/member/delete';
                    const fetchOption = {
                        method: "POST",
                        headers: {
                            'Content-Type': 'application/json' // 요청 헤더에 Content-Type을 추가
                        },
                        body: JSON.stringify({
                            id: mid, // 서버에서 받을 값(name)
                            role: mLeader
                        }),
                    }
                    fetch(url, fetchOption)
                        .then((response) => response.json())
                        .then((result) => {

                            if (result.success === 1) {
                                console.log(result);
                                const updateLeaderNone = document.getElementById('updateLeaderDiv');
                                const updateSelectLeader = document.getElementById('updateSelectLeaderDiv');
                                updateLeaderNone.style.display = 'none';
                                updateSelectLeader.style.display = 'flex';
                                // 팀장 선택을 위한 코드
                                const memberInfos = teamCard.querySelector('.teamMemberRow');
                                const memberInfo = memberInfos.getElementsByClassName('memberInfo');
                                const leaderOption = document.getElementById('updateSelectLeader');
                                for (const info of memberInfo) {
                                    const memberNameInfoValue = info.querySelector('.teamMember');
                                    const memberNameInfo = memberNameInfoValue.textContent;
                                    const memberIdInfoValue = info.querySelector('.memberId');
                                    const memberIdInfo = memberIdInfoValue.textContent;
                                    // 팀장 선택을 위한 옵션 만들기
                                    const option = document.createElement('option');
                                    option.value = memberIdInfo;
                                    option.textContent = memberNameInfo;
                                    leaderOption.appendChild(option);
                                }
                                // 방금 삭제된 값을 재삽입
                                const option = document.createElement('option');
                                option.value = mid;
                                option.textContent = "되돌리기";
                                leaderOption.appendChild(option);
                                // 팀원이 된 팀장 팀원 리스트에 추가
                                // 팀원 삭제를 위한 팀원 나열
                                const teamMembers = document.createElement('div');
                                teamMembers.className = "deleteTeamMemberDiv";
                                // span 태그 생성 후 팀원 이름 세팅
                                const memberSpanTag = document.createElement('span');
                                memberSpanTag.textContent = (document.getElementById('updateLeader').textContent = teamLeaderNameValue.textContent);
                                teamMembers.appendChild(memberSpanTag);
                                // 삭제를 위한 버튼 생성
                                const memberDeleteBtn = document.createElement('button');
                                memberDeleteBtn.setAttribute('data-mid', mid);
                                memberDeleteBtn.type = "button";
                                memberDeleteBtn.textContent = "삭제";
                                teamMembers.appendChild(memberDeleteBtn);
                                membersInfo.appendChild(teamMembers);
                            } else if (result.success === 2) {
                                console.log(result);
                                // 서버 요청 성공. DOM 요소 제거
                                document.querySelector(`[data-mid="${mid}"]`).closest('div').remove();
                            } else {
                                alert('삭제 실패!');
                                return false;
                            }

                        }).catch((error) => {
                        console.error('Error:', error);
                        alert('삭제 실패!');
                    });
                };

                const memberEl = document.querySelectorAll('[data-mid]');
                memberEl.forEach((item) => {
                    item.addEventListener('click', (e) => {
                        const el = e.target;
                        deleteMember(el.dataset.mid, el.dataset.mrole ?? null);
                    });
                });
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
    const deleteButtons = document.querySelectorAll('.teamDeleteBtn');
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

function addMemberSelect() {
    var container = document.getElementById("memberContainer");
    var memberSelect = document.createElement("select");
    var options = document.getElementById("leader").getElementsByTagName("option");

    memberSelect.name = "members[]";
    memberSelect.className = "member-select";

    // 기본 옵션 추가
    var defaultOption = document.createElement("option");
    defaultOption.value = "";
    defaultOption.disabled = true;
    defaultOption.selected = true;
    defaultOption.text = "팀원 선택";
    memberSelect.appendChild(defaultOption);

    // 직원 목록 옵션 추가
    for (var i = 0; i < options.length; i++) {
        if (options[i].value && !options[i].disabled) {
            var option = document.createElement("option");
            option.value = options[i].value;
            option.text = options[i].text;
            memberSelect.appendChild(option);
        }
    }

    container.appendChild(memberSelect);
    updateOptions();
}

// 선택한 이름은 옵션에서 제거
function updateOptions() {
    var leaderSelect = document.getElementById("leader");
    var leaderValue = leaderSelect.value.split('|')[1];

    var selectedMembers = [];
    var memberSelects = document.getElementsByClassName("member-select")

    // 팀원으로 선택되면 옵션에서 숨김
    for (var i = 0; i < memberSelects.length; i++) {
        var selectedValue = memberSelects[i].value.split('|')[1];
        if (selectedValue) {
            selectedMembers.push(selectedValue);
        }
    }
    // 팀장으로 선택되면 옵션에서 숨김
    for (var i = 0; i < memberSelects.length; i++) {
        var options = memberSelects[i].getElementsByTagName('option');
        for (var j = 0; j < options.length; j++) {
            var optionValue = options[j].value.split('|')[1];
            if (optionValue === leaderValue || selectedMembers.includes(optionValue)) {
                options[j].style.display = 'none';
            } else {
                options[j].style.display = 'block';
            }
        }
    }
}

// 팀 등록 모달창을 열기 위한 코드
const writeModal = document.querySelector('.writeModal');
const writeModalCloseOpen = document.querySelector('.teamWriteBtn');

writeModalCloseOpen.addEventListener("click", () => {
    writeModal.style.display = "flex";
    document.body.style.overflow = "hidden";
});

// 팀장 선택이 제대로 되었는지 확인하는 코드
function validateSelection() {
    var selectElement = document.getElementById("leader");
    var selectedValue = selectElement.options[selectElement.selectedIndex].value;
    var memberSelect = document.querySelector(".member-select");

    if (selectedValue === "") {
        alert("팀장을 선택해 주세요.");
        return false;
    }

    if (!memberSelect) {
        alert("팀원을 추가해 주세요.");
        return false;
    }
    return true;
}