// Theme colors
const ThemeColor = {
    GREEN: { color: '#4CAF50', name: 'Green' },
    BLUE: { color: '#2196F3', name: 'Blue' },
    PURPLE: { color: '#9C27B0', name: 'Purple' },
    ORANGE: { color: '#FF9800', name: 'Orange' },
    TEAL: { color: '#009688', name: 'Teal' }
};

// Sort order
const SortOrder = {
    ASCENDING: 'ASCENDING',
    DESCENDING: 'DESCENDING'
};

// State
let words = [];
let filteredWords = [];
let query = '';
let mode = 'prefix';
let noVocal = false;
let sortOrder = SortOrder.ASCENDING;
let selectedTheme = ThemeColor.GREEN;

// DOM Elements
const wordCountEl = document.getElementById('wordCount');
const searchInput = document.getElementById('searchInput');
const clearButton = document.getElementById('clearButton');
const themeButton = document.getElementById('themeButton');
const themeDropdown = document.getElementById('themeDropdown');
const themeDot = document.getElementById('themeDot');
const recommendations = document.querySelectorAll('.chip');
const modeButtons = document.querySelectorAll('.mode-button');
const sortButton = document.getElementById('sortButton');
const sortIcon = document.getElementById('sortIcon');
const sortText = document.getElementById('sortText');
const sortDropdown = document.getElementById('sortDropdown');
const sortOptions = document.querySelectorAll('.sort-option');
const novowelSwitch = document.getElementById('novowelSwitch');
const resultsHeader = document.getElementById('resultsHeader');
const resultsCount = document.getElementById('resultsCount');
const novowelIndicator = document.getElementById('novowelIndicator');
const resultsList = document.getElementById('resultsList');
const loading = document.getElementById('loading');

// Helper Functions
function isNoVowel(word) {
    return !/[aiueo]/.test(word);
}

function updateTheme(color) {
    document.documentElement.style.setProperty('--accent-color', color.color);
    document.documentElement.style.setProperty('--accent-light', color.color + '26'); // 15% opacity
    themeDot.style.backgroundColor = color.color;
    
    // Update active state in dropdown
    document.querySelectorAll('.theme-option').forEach(opt => {
        opt.classList.remove('active');
        if (opt.dataset.color === Object.keys(ThemeColor).find(key => ThemeColor[key] === color).toLowerCase()) {
            opt.classList.add('active');
        }
    });
}

function filterWords() {
    if (!query) {
        filteredWords = [];
        renderResults();
        return;
    }

    const searchTerm = query.toLowerCase();
    
    let results = words.filter(word => {
        if (mode === 'prefix') {
            return word.startsWith(searchTerm);
        } else {
            return word.endsWith(searchTerm);
        }
    });

    // Sort
    results.sort((a, b) => {
        if (sortOrder === SortOrder.ASCENDING) {
            return a.localeCompare(b);
        } else {
            return b.localeCompare(a);
        }
    });

    // No vowel priority
    if (noVocal) {
        const goldWords = results.filter(word => isNoVowel(word));
        const normalWords = results.filter(word => !isNoVowel(word));
        results = [...goldWords, ...normalWords];
    }

    filteredWords = results;
    renderResults();
}

function renderResults() {
    // Update results header
    if (query) {
        resultsHeader.style.display = 'flex';
        resultsCount.textContent = `${filteredWords.length} hasil`;
        novowelIndicator.style.display = noVocal ? 'block' : 'none';
    } else {
        resultsHeader.style.display = 'none';
    }

    // Clear results
    resultsList.innerHTML = '';

    if (filteredWords.length === 0 && query) {
        resultsList.innerHTML = '<div class="no-results">Kata tidak ditemukan</div>';
        return;
    }

    // Group by last letter for suffix mode
    if (mode === 'suffix' && filteredWords.length > 0) {
        const grouped = {};
        filteredWords.forEach(word => {
            const lastLetter = word[word.length - 1];
            if (!grouped[lastLetter]) {
                grouped[lastLetter] = [];
            }
            grouped[lastLetter].push(word);
        });

        // Sort keys
        const sortedKeys = Object.keys(grouped).sort();

        sortedKeys.forEach(letter => {
            const words = grouped[letter];
            
            // Add group header
            const header = document.createElement('div');
            header.className = 'group-header';
            header.innerHTML = `
                <div class="group-line"></div>
                <span class="group-letter">${letter.toUpperCase()}</span>
                <div class="group-line"></div>
            `;
            resultsList.appendChild(header);

            // Add words for this group (max 20)
            words.slice(0, 20).forEach(word => {
                const card = createWordCard(word, noVocal && isNoVowel(word));
                resultsList.appendChild(card);
            });
        });
    } else {
        // Normal display (max 200 words)
        filteredWords.slice(0, 200).forEach(word => {
            const card = createWordCard(word, noVocal && isNoVowel(word));
            resultsList.appendChild(card);
        });
    }
}

function createWordCard(word, isGold) {
    const card = document.createElement('div');
    card.className = `word-card${isGold ? ' gold' : ''}`;
    card.textContent = word;
    return card;
}

// Load words from file
async function loadWords() {
    try {
        loading.style.display = 'flex';
        const response = await fetch('data/kbbi.txt');
        const text = await response.text();
        
        words = text.split('\n')
            .map(line => line.trim().toLowerCase())
            .filter(word => /^[a-zA-Z]+$/.test(word) && word.length > 2);
        
        wordCountEl.textContent = `${words.length} kata tersedia`;
    } catch (error) {
        console.error('Error loading words:', error);
        wordCountEl.textContent = 'Gagal memuat kata';
    } finally {
        loading.style.display = 'none';
    }
}

// Event Listeners
searchInput.addEventListener('input', (e) => {
    query = e.target.value;
    filterWords();
});

clearButton.addEventListener('click', () => {
    searchInput.value = '';
    query = '';
    filterWords();
    // Remove active class from chips
    recommendations.forEach(chip => chip.classList.remove('active'));
});

// Theme dropdown
themeButton.addEventListener('click', (e) => {
    e.stopPropagation();
    themeDropdown.classList.toggle('show');
});

document.querySelectorAll('.theme-option').forEach(option => {
    option.addEventListener('click', () => {
        const colorName = option.dataset.color;
        const theme = ThemeColor[colorName.toUpperCase()];
        selectedTheme = theme;
        updateTheme(theme);
        themeDropdown.classList.remove('show');
    });
});

// Recommendations
recommendations.forEach(chip => {
    chip.addEventListener('click', () => {
        const value = chip.dataset.value;
        searchInput.value = value;
        query = value;
        
        // Update active state
        recommendations.forEach(c => c.classList.remove('active'));
        chip.classList.add('active');
        
        filterWords();
    });
});

// Mode toggle
modeButtons.forEach(button => {
    button.addEventListener('click', () => {
        modeButtons.forEach(b => b.classList.remove('active'));
        button.classList.add('active');
        mode = button.dataset.mode;
        filterWords();
    });
});

// Sort dropdown
sortButton.addEventListener('click', (e) => {
    e.stopPropagation();
    sortDropdown.classList.toggle('show');
});

sortOptions.forEach(option => {
    option.addEventListener('click', () => {
        const sort = option.dataset.sort;
        sortOrder = sort === 'asc' ? SortOrder.ASCENDING : SortOrder.DESCENDING;
        
        // Update UI
        sortIcon.className = sort === 'asc' ? 'fas fa-arrow-up' : 'fas fa-arrow-down';
        sortText.textContent = sort === 'asc' ? 'A-Z' : 'Z-A';
        
        // Update active state
        sortOptions.forEach(opt => opt.classList.remove('active'));
        option.classList.add('active');
        
        sortDropdown.classList.remove('show');
        filterWords();
    });
});

// No vowel toggle
novowelSwitch.addEventListener('change', (e) => {
    noVocal = e.target.checked;
    novowelIndicator.style.display = noVocal ? 'block' : 'none';
    filterWords();
});

// Close dropdowns when clicking outside
document.addEventListener('click', () => {
    themeDropdown.classList.remove('show');
    sortDropdown.classList.remove('show');
});

// Initialize
loadWords();
updateTheme(selectedTheme);