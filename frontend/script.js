// Add click event listener to the "Find Career" button
document.getElementById('findCareerBtn').addEventListener('click', async () => {
    const skillsInput = document.getElementById('skills').value.trim();  // Get user input
    const careerList = document.getElementById('career-list');  // Career results container
    const careerSection = document.getElementById('career-section');  // Section to display results

    // Clear previous results
    careerList.innerHTML = '';

    // Show an alert if no skills are entered
    if (!skillsInput) {
        alert("⚠️ Please enter at least one skill.");
        return;
    }

    // Convert input into an array (split by commas, trim spaces, and convert to lowercase)
    const skillArray = skillsInput.split(',').map(skill => skill.trim().toLowerCase());

    try {
        console.log("📤 Sending request:", JSON.stringify({ skills: skillArray }));

        // Send a POST request to the backend
        const response = await fetch("http://localhost:5000/career", {  
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ skills: skillArray })  // Convert skills to JSON format
        });

        console.log("📥 Raw Response:", response);

        // Check if response is successful
        if (!response.ok) {
            throw new Error(`Server Error: ${response.status}`);
        }

        // Parse response JSON
        const data = await response.json();
        console.log("📥 Parsed Response:", data);

        // Display career recommendations
        if (!data.careers || data.careers.length === 0) {
            careerList.innerHTML = "<li>❌ No career matches found for your skills.</li>";
        } else {
            data.careers.forEach(career => {
                const listItem = document.createElement('li');
                listItem.textContent = career;
                careerList.appendChild(listItem);
            });
        }

        // Show the career results section
        careerSection.style.display = "block";  

    } catch (error) {
        console.error('❌ Error fetching career data:', error);
        careerList.innerHTML = `<li>⚠️ Error: ${error.message}</li>`;
    }
});
