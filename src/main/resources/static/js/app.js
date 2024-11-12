// Add a new header input row
function addHeader() {
    const headersContainer = document.getElementById('headersContainer');
    const headerRow = document.createElement('div');
    headerRow.className = 'input-group mb-2';
    headerRow.innerHTML = `
        <input type="text" class="form-control" placeholder="Header Key">
        <input type="text" class="form-control" placeholder="Header Value">
        <div class="input-group-append">
            <button type="button" class="btn btn-danger" onclick="removeHeader(this)">Remove</button>
        </div>
    `;
    headersContainer.appendChild(headerRow);
}

// Remove a header input row
function removeHeader(button) {
    button.closest('.input-group').remove();
}

// Collect header data from input fields
function getHeaders() {
    const headers = {};
    const headerRows = document.querySelectorAll('#headersContainer .input-group');
    headerRows.forEach(row => {
        const key = row.children[0].value.trim();
        const value = row.children[1].value.trim();
        if (key) {
            headers[key] = value;
        }
    });
    return headers;
}

// Handle form submission for creating an endpoint
document.getElementById('createEndpointForm').addEventListener('submit', function(event) {
    event.preventDefault();
// Helper function to parse JSON safely
    function parseJsonObject(input) {
        try {
            return JSON.parse(input) || {};
        } catch (e) {
            console.error("Invalid JSON format in response fields", e);
            return {};
        }
    }

   const endpointData = {
           projectName: document.getElementById('projectName').value,
           path: document.getElementById('path').value,
           method: document.getElementById('method').value,
           headers: getHeaders(),
           successStatus: parseInt(document.getElementById('successStatus').value, 10), // Convert to integer
           successResponse: parseJsonObject(document.getElementById('successResponse').value), // Parse as JSON
           errorStatus: parseInt(document.getElementById('errorStatus').value, 10),     // Convert to integer
           errorResponse: parseJsonObject(document.getElementById('errorResponse').value) // Parse as JSON
       };

    fetch('/api/simulator/configure', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(endpointData)
    })
    .then(response => response.json())
    .then(data => {
        alert('Endpoint created successfully!');
        document.getElementById('createEndpointForm').reset();
        document.getElementById('headersContainer').innerHTML = '';  // Clear header fields
        addHeader();  // Add an initial empty header row
    })
    .catch(error => console.error('Error:', error));
});

// Fetch and display endpoints by project
function viewEndpoints() {
    const projectName = document.getElementById('viewProjectName').value;

    fetch(`/api/simulator/${projectName}/endpoints`)
    .then(response => response.json())
    .then(data => {
        const endpointsList = document.getElementById('endpointsList');
        endpointsList.innerHTML = '';

        if (data.length === 0) {
            endpointsList.innerHTML = '<p>No endpoints found for this project.</p>';
            return;
        }

        data.forEach(endpoint => {
            const endpointCard = document.createElement('div');
            endpointCard.classList.add('card', 'mb-3');
            endpointCard.innerHTML = `
                <div class="card-body">
                    <h5 class="card-title">${endpoint.path} (${endpoint.method})</h5>
                    <p class="card-text"><strong>Headers:</strong> ${JSON.stringify(endpoint.headers)}</p>
                    <p class="card-text"><strong>Success Status:</strong> ${endpoint.successStatus}</p>
                    <p class="card-text"><strong>Success Response:</strong> ${endpoint.successResponse}</p>
                    <p class="card-text"><strong>Error Status:</strong> ${endpoint.errorStatus}</p>
                    <p class="card-text"><strong>Error Response:</strong> ${endpoint.errorResponse}</p>
                </div>
            `;
            endpointsList.appendChild(endpointCard);
        });
    })
    .catch(error => console.error('Error:', error));
}

// Initialize with one header row
addHeader();
