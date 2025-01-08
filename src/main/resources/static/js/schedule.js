document.addEventListener("DOMContentLoaded", function () {
    // sList를 JSON 형식으로 변환
    const scheduleYear = document.getElementById('scheduleYear');
    const scheduleMonth = document.getElementById('scheduleMonth');
    const today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1;
    let leapMonth = year - 2020;
    // 달 이동 처리를 거쳤을 경우
    // 쿠키에서 특정 값을 가져오는 함수
    function getCookie(name) {
        const nameEQ = name + "=";
        const ca = document.cookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) === ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    }

    // 쿠키에서 'month' 값 가져오기
    const cookieMonth = getCookie('month');
    if (cookieMonth) {
        const parsedMonth = parseInt(cookieMonth, 10);
        if (!isNaN(parsedMonth)) {
            month = parsedMonth;
        } else {
            console.error('Invalid month value in cookie:', cookieMonth);
        }
    }
    // 쿠키에서 'year' 값 가져오기
    const cookieYear = getCookie('year');
    if (cookieYear) {
        const parsedYear = parseInt(cookieYear, 10);
        leapMonth = parsedYear - 2020;
        if (!isNaN(parsedYear)) {
            year = parsedYear;
        } else {
            console.error('Invalid month value in cookie:', parsedYear);
        }
    }
    scheduleYear.textContent = year;
    scheduleMonth.textContent = month + '월';

    // 이전 달로 이동을 위한 처리
    document.getElementById('monthLeftBtn').addEventListener('click', monthLeftSelect);

    function monthLeftSelect() {
        if (month === 1) {
            let lastYearMonth = 12;
            let lastYear = year - 1;
            document.cookie = `month=${lastYearMonth}; path=/schedule`;
            document.cookie = `year=${lastYear}; path=/schedule`;
        } else {
            let leftMonth = month - 1;
            document.cookie = `month=${leftMonth}; path=/schedule`;
        }
        location.href = '/schedule/list';
    }

    // 다음 달로 이동을 위한 처리
    document.getElementById('monthRightBtn').addEventListener('click', monthRightSelect);

    function monthRightSelect() {
        if (month === 12) {
            let nextYearMonth = 1;
            let nextYear = year + 1;
            document.cookie = `month=${nextYearMonth}; path=/schedule`;
            document.cookie = `year=${nextYear}; path=/schedule`;
        } else {
            let RightMonth = month + 1;
            document.cookie = `month=${RightMonth}; path=/schedule`;
        }
        location.href = '/schedule/list';
    }

    const teamListDivs = document.querySelectorAll('.teamListDiv');
    let totalEmployeeBoxCount = 0;
    // 직원번호 저장을 위한 배열
    const employeeIds = [];

    teamListDivs.forEach(function (teamListDiv) {
        const teamBox = teamListDiv.querySelector('.teamBox');
        const employeeBoxes = teamListDiv.querySelectorAll('.employeeBox');

        const employeeBoxCount = employeeBoxes.length;
        totalEmployeeBoxCount += employeeBoxCount;

        const employeeBoxHeight = employeeBoxes[0].getBoundingClientRect().height;
        const totalHeight = employeeBoxes.length * employeeBoxHeight;
        teamBox.style.height = totalHeight + 'px';

        // 직원 박스에서 직원 번호 추출
        employeeBoxes.forEach(function (employeeBox) {
            const employeeId = employeeBox.getAttribute('data-employee-id');
            employeeIds.push(employeeId);
        });
    });

    for (let i = 1; i <= totalEmployeeBoxCount; i++) {
        const scheduleDayBox = document.createElement('div');
        scheduleDayBox.className = 'scheduleDayBox';
        scheduleDaysBox.appendChild(scheduleDayBox);
        if (employeeIds[i - 1]) {
            if ([1, 3, 5, 7, 8, 10, 12].includes(month)) { // month가 1, 3, 5, 7, 8, 10, 12월 중 하나인 경우
                for (let j = 1; j <= 31; j++) {
                    const closeDayBox = document.createElement('div');
                    closeDayBox.className = 'closeDayBox';

                    const hiddenData = document.createElement('input');
                    hiddenData.type = 'hidden';
                    hiddenData.value = `${year}-${month}-${j}`;
                    hiddenData.name = 'employeeId';
                    hiddenData.setAttribute('data-employee-id', employeeIds[i - 1]);

                    // sList와 날짜, 직원 번호 대조 후 색상 변경
                    const dateString = `${year}-${String(month).padStart(2, '0')}-${String(j).padStart(2, '0')}`;
                    const employeeIdData = String(employeeIds[i - 1]);
                    sList.forEach(item => {
                        const itemDateString = new Date(item.closedDay).toISOString().split('T')[0];
                        const itemImployeeId = String(item.employeeId);
                        const itemScheduleId = String(item.scheduleId);
                        if (itemImployeeId === employeeIdData && itemDateString === dateString) {
                            closeDayBox.style.backgroundColor = '#FFEB00';
                            closeDayBox.textContent = '휴무';
                            const hiddenId = document.createElement('input');
                            hiddenId.type = 'hidden';
                            hiddenId.name = 'scheduleId';
                            hiddenId.setAttribute('data-schedule-id', itemScheduleId);
                            closeDayBox.appendChild(hiddenId);
                        }
                    });

                    scheduleDayBox.appendChild(closeDayBox);
                    closeDayBox.appendChild(hiddenData);
                }
            }
            if ([4, 6, 9, 11].includes(month)) { // month가 4, 6, 9, 11월 중 하나인 경우
                for (let j = 1; j <= 30; j++) {
                    const closeDayBox = document.createElement('div');
                    closeDayBox.className = 'closeDayBox';

                    const hiddenData = document.createElement('input');
                    hiddenData.type = 'hidden';
                    hiddenData.value = `${year}-${month}-${j}`;
                    hiddenData.name = 'employeeId';
                    hiddenData.setAttribute('data-employee-id', employeeIds[i - 1]);

                    // sList와 날짜, 직원 번호 대조 후 색상 변경
                    const dateString = `${year}-${String(month).padStart(2, '0')}-${String(j).padStart(2, '0')}`;
                    const employeeIdData = String(employeeIds[i - 1]);
                    sList.forEach(item => {
                        const itemDateString = new Date(item.closedDay).toISOString().split('T')[0];
                        const itemImployeeId = String(item.employeeId);
                        const itemScheduleId = String(item.scheduleId);
                        if (itemImployeeId === employeeIdData && itemDateString === dateString) {
                            closeDayBox.style.backgroundColor = '#FFEB00';
                            closeDayBox.textContent = '휴무';
                            const hiddenId = document.createElement('input');
                            hiddenId.type = 'hidden';
                            hiddenId.name = 'scheduleId';
                            hiddenId.setAttribute('data-schedule-id', itemScheduleId);
                            closeDayBox.appendChild(hiddenId);
                        }
                    });

                    scheduleDayBox.appendChild(closeDayBox);
                    closeDayBox.appendChild(hiddenData);
                }
            }
            if (month === 2) { // month가 2월인 경우
                if (leapMonth % 4 === 0) {
                    for (let j = 1; j <= 29; j++) {
                        const closeDayBox = document.createElement('div');
                        closeDayBox.className = 'closeDayBox';

                        const hiddenData = document.createElement('input');
                        hiddenData.type = 'hidden';
                        hiddenData.value = `${year}-${month}-${j}`;
                        hiddenData.name = 'employeeId';
                        hiddenData.setAttribute('data-employee-id', employeeIds[i - 1]);

                        // sList와 날짜, 직원 번호 대조 후 색상 변경
                        const dateString = `${year}-${String(month).padStart(2, '0')}-${String(j).padStart(2, '0')}`;
                        const employeeIdData = String(employeeIds[i - 1]);
                        sList.forEach(item => {
                            const itemDateString = new Date(item.closedDay).toISOString().split('T')[0];
                            const itemImployeeId = String(item.employeeId);
                            const itemScheduleId = String(item.scheduleId);
                            if (itemImployeeId === employeeIdData && itemDateString === dateString) {
                                closeDayBox.style.backgroundColor = '#FFEB00';
                                closeDayBox.textContent = '휴무';
                                const hiddenId = document.createElement('input');
                                hiddenId.type = 'hidden';
                                hiddenId.name = 'scheduleId';
                                hiddenId.setAttribute('data-schedule-id', itemScheduleId);
                                closeDayBox.appendChild(hiddenId);
                            }
                        });

                        scheduleDayBox.appendChild(closeDayBox);
                        closeDayBox.appendChild(hiddenData);
                    }
                } else {
                    for (let j = 1; j <= 28; j++) {
                        const closeDayBox = document.createElement('div');
                        closeDayBox.className = 'closeDayBox';

                        const hiddenData = document.createElement('input');
                        hiddenData.type = 'hidden';
                        hiddenData.value = `${year}-${month}-${j}`;
                        hiddenData.name = 'employeeId';
                        hiddenData.setAttribute('data-employee-id', employeeIds[i - 1]);

                        // sList와 날짜, 직원 번호 대조 후 색상 변경
                        const dateString = `${year}-${String(month).padStart(2, '0')}-${String(j).padStart(2, '0')}`;
                        const employeeIdData = String(employeeIds[i - 1]);
                        sList.forEach(item => {
                            const itemDateString = new Date(item.closedDay).toISOString().split('T')[0];
                            const itemImployeeId = String(item.employeeId);
                            const itemScheduleId = String(item.scheduleId);
                            if (itemImployeeId === employeeIdData && itemDateString === dateString) {
                                closeDayBox.style.backgroundColor = '#FFEB00';
                                closeDayBox.textContent = '휴무';
                                const hiddenId = document.createElement('input');
                                hiddenId.type = 'hidden';
                                hiddenId.name = 'scheduleId';
                                hiddenId.setAttribute('data-schedule-id', itemScheduleId);
                                closeDayBox.appendChild(hiddenId);
                            }
                        });

                        scheduleDayBox.appendChild(closeDayBox);
                        closeDayBox.appendChild(hiddenData);
                    }
                }
            }
        }
    }
    // 휴무일 추가를 위한 이벤트 리스너 추가
    const closeDayBoxes = document.getElementsByClassName("closeDayBox");
    Array.from(closeDayBoxes).forEach(function (closeDayBox) {
        closeDayBox.addEventListener('click', closed);
    });

    function closed() {
        const closeDayBox = event.target;
        const cellElement = closeDayBox.querySelector('input[name="employeeId"]');
        const cellEmployeeId = cellElement.dataset.employeeId;
        const cellElementValue = cellElement.value;
        console.log(cellElementValue);
        console.log(cellEmployeeId);

        const deleteElement = closeDayBox.querySelector('input[name="scheduleId"]');
        if (!deleteElement) {
            // 스케줄 추가 비동기 통신
            fetch('/close/write', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    employeeId: cellEmployeeId,
                    closedDay: cellElementValue
                })
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Success:', data);
                    // 휴무 구분을 위한 추가
                    closeDayBox.style.backgroundColor = '#FFEB00';
                    closeDayBox.textContent = '휴무';
                    const createInput = document.createElement('input');
                    createInput.type = 'hidden';
                    createInput.value = cellElementValue;
                    createInput.name = 'employeeId';
                    createInput.setAttribute('data-employee-id', cellEmployeeId);
                    closeDayBox.appendChild(createInput);
                    const createHiddenId = document.createElement('input');
                    createHiddenId.type = 'hidden';
                    createHiddenId.name = 'scheduleId';
                    console.log(data.success.scheduleId);
                    createHiddenId.setAttribute('data-schedule-id', data.success.scheduleId);
                    closeDayBox.appendChild(createHiddenId);
                })
                .catch((error) => {
                    console.error('Error:', error);
                    alert('추가 실패!');
                });
        } else {
            const deleteScheduleId = deleteElement.dataset.scheduleId;
            // 스케줄 삭제 비동기 통신
            fetch('/close/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    scheduleId: deleteScheduleId
                })
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Success:', data);
                    // 휴무 삭제 구분을 위한 변경
                    closeDayBox.style.backgroundColor = '';
                    closeDayBox.textContent = '';
                    const deleteInput = document.createElement('input');
                    deleteInput.type = 'hidden';
                    deleteInput.value = cellElementValue;
                    deleteInput.name = 'employeeId';
                    deleteInput.setAttribute('data-employee-id', cellEmployeeId);
                    closeDayBox.appendChild(deleteInput);
                })
                .catch((error) => {
                    console.error('Error:', error);
                    alert('추가 실패!');
                });
        }
    }


    if ([1, 3, 5, 7, 8, 10, 12].includes(month)) { // month가 1, 3, 5, 7, 8, 10, 12월인 경우
        for (let i = 1; i <= 31; i++) {
            const dayBox = document.createElement('div');
            dayBox.className = 'dayBox';
            dayBox.textContent = i;
            dayListBox.appendChild(dayBox);
        }
    }
    if ([4, 6, 9, 11].includes(month)) { // month가 4, 6, 9, 11월인 경우
        for (let i = 1; i <= 30; i++) {
            const dayBox = document.createElement('div');
            dayBox.className = 'dayBox';
            dayBox.textContent = i;
            dayListBox.appendChild(dayBox);
        }
    }
    if (month === 2) { // month가 2월인 경우
        if (leapMonth % 4 === 0) {
            for (let i = 1; i <= 29; i++) {
                const dayBox = document.createElement('div');
                dayBox.className = 'dayBox';
                dayBox.textContent = i;
                dayListBox.appendChild(dayBox);
            }
        } else {
            for (let i = 1; i <= 28; i++) {
                const dayBox = document.createElement('div');
                dayBox.className = 'dayBox';
                dayBox.textContent = i;
                dayListBox.appendChild(dayBox);
            }
        }
    }

    // 팀별로 보기를 위한 처리
    const teamScheduleSelect = document.getElementById('teamSchedule');
    // select box에 선택된 값을 세팅
    const urlParams = new URLSearchParams(window.location.search);
    const savedValue = urlParams.get('teamValue');

    if (savedValue) {
        teamScheduleSelect.value = savedValue;
    }
    teamScheduleSelect.addEventListener('change', function (event) {
        const selectedValue = event.target.value;
        // 선택된 값을 저장
        localStorage.setItem('selectedTeamValue', selectedValue);
        if (selectedValue === '전체') {
            document.location.href = '/schedule/list';
        } else {
            document.location.href = '/schedule/list?teamValue=' + selectedValue;
        }
    });

    // 특정 쿠키를 삭제하는 함수
    function deleteCookie(name) {
        document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/schedule; domain=' + window.location.hostname + ';';
    }

    // 'month' 쿠키 삭제
    deleteCookie('month');
    // 'year' 쿠키 삭제
    document.getElementById('scheduleBtn').addEventListener('click', deleteCookieYear);
    document.getElementById('aTagBtnEmployee').addEventListener('click', deleteCookieYear);
    document.getElementById('aTagBtnTeam').addEventListener('click', deleteCookieYear);

    function deleteCookieYear() {
        deleteCookie('year');
    }
});