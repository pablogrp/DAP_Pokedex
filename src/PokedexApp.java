import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Arrays;
import java.util.List;

public class PokedexApp extends JFrame {

    private final PokeApiClient pokeApiClient;
    private JComboBox<String> generationDropdown;
    private JComboBox<String> pokemonDropdown;
    private JComboBox<String> berryDropdown;
    private JComboBox<String> pokeballDropdown;
    private JTextArea pokemonInfoArea;
    private JTextArea berryInfoArea;
    private JTextArea pokeballInfoArea;
    private JLabel pokemonSpriteLabel;
    private JLabel berrySpriteLabel;
    private JLabel pokeballSpriteLabel;
    private PokemonFactory pokemonFactory;
    private int genSelected;

    public PokedexApp(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
        setTitle("Pokedex");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado para permitir cerrar instancias de comparación sin cerrar toda la app
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        JPanel topPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // Cambiado a 5 filas para incluir el botón "Comparar"

        // Dropdown para generaciones
        generationDropdown = new JComboBox<>(new String[]{
                "Generation 1", "Generation 5", "Generation 9"});
        generationDropdown.addActionListener(new GenerationSelectionListener());
        topPanel.add(new JLabel("Select Generation:"));
        topPanel.add(generationDropdown);

        // Dropdown para pokemons
        pokemonDropdown = new JComboBox<>();
        pokemonDropdown.addActionListener(new PokemonSelectionListener());
        topPanel.add(new JLabel("Select Pokemon:"));
        topPanel.add(pokemonDropdown);

        // Dropdown para bayas
        berryDropdown = new JComboBox<>();
        berryDropdown.addActionListener(new BerrySelectionListener());
        topPanel.add(new JLabel("Select Berry:"));
        topPanel.add(berryDropdown);

        // Dropdown para Poké Balls
        pokeballDropdown = new JComboBox<>();
        pokeballDropdown.addActionListener(new PokeballSelectionListener());
        topPanel.add(new JLabel("Select Poké Ball:"));
        topPanel.add(pokeballDropdown);

        // Botón de Comparar
        JButton compareButton = new JButton("Compare");
        compareButton.addActionListener(new CompareButtonListener());
        topPanel.add(compareButton);

        add(topPanel, BorderLayout.NORTH);

        // Crear áreas de información
        pokemonInfoArea = new JTextArea();
        pokemonInfoArea.setEditable(false);
        berryInfoArea = new JTextArea();
        berryInfoArea.setEditable(false);
        pokeballInfoArea = new JTextArea();
        pokeballInfoArea.setEditable(false);

        // Panel para mostrar información y sprites
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // Organiza las áreas y etiquetas de sprites

        pokemonSpriteLabel = new JLabel();
        berrySpriteLabel = new JLabel();
        pokeballSpriteLabel = new JLabel();

        infoPanel.add(pokemonSpriteLabel);
        infoPanel.add(new JScrollPane(pokemonInfoArea));
        infoPanel.add(berrySpriteLabel);
        infoPanel.add(new JScrollPane(berryInfoArea));
        infoPanel.add(pokeballSpriteLabel);
        infoPanel.add(new JScrollPane(pokeballInfoArea));

        add(infoPanel, BorderLayout.CENTER);
    }

    // Listener para el botón de comparación, crea una nueva instancia de la ventana
    private class CompareButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PokedexApp comparisonWindow = new PokedexApp(pokeApiClient); // Crear nueva instancia de PokedexApp
            comparisonWindow.setVisible(true); // Mostrar la nueva ventana
        }
    }

    private class GenerationSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (generationDropdown.getSelectedIndex()) {
                case 0:
                    pokemonFactory = new FirstGenFactory();
                    genSelected = 1;
                    break;
                case 1:
                    pokemonFactory = new FifthGenFactory();
                    genSelected = 5;
                    break;
                case 2:
                    pokemonFactory = new NinthGenFactory();
                    genSelected = 9;
                    break;
            }
            try {
                List<String> pokemonNames = pokeApiClient.getAllPokemonNames(genSelected);
                pokemonDropdown.removeAllItems();
                for (String name : pokemonNames) {
                    pokemonDropdown.addItem(name);
                }

                List<String> berriesNames = pokeApiClient.getAllBerryNames(genSelected);
                berryDropdown.removeAllItems();
                for (String name : berriesNames) {
                    berryDropdown.addItem(name);
                }

                List<String> pokeballsNames = pokeApiClient.getAllPokeballNames(genSelected);
                pokeballDropdown.removeAllItems();
                for (String name : pokeballsNames) {
                    pokeballDropdown.addItem(name);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(PokedexApp.this, "Error loading Pokémon list for selected generation", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private class BerrySelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedBerry = (String) berryDropdown.getSelectedItem();
            Berry berry = null;
            if (selectedBerry != null) {
                try {
                    String[] berryData = pokeApiClient.getBerryData(selectedBerry);
                    berry = pokemonFactory.createBerry(berryData[0], berryData[1], berryData[2], Integer.parseInt(berryData[3]));
                    loadSprite(berry.getSprite(), berrySpriteLabel);
                    displayBerryInfo(berry);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PokedexApp.this, "Error retrieving Poké Ball information", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    private class PokemonSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedPokemon = (String) pokemonDropdown.getSelectedItem();
            if (selectedPokemon != null) {
                Pokemon pokemon = null;

                try {
                    String[] pokemonData = pokeApiClient.getPokemonData(selectedPokemon);
                    int[] stats;
                    String[] types;
                    stats = new int[6];
                    for (int i = 2; i < 8; i++) {
                        stats[i - 2] = Integer.parseInt(pokemonData[i]);
                    }
                    types = pokemonData[8].split(",");
                    pokemon = pokemonFactory.createPokemon(pokemonData[0], pokemonData[1], stats, types);

                    loadSprite(pokemonData[1], pokemonSpriteLabel);
                    displayPokemonInfo(pokemon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PokedexApp.this, "Error retrieving Pokémon information", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    private class PokeballSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedPokeball = (String) pokeballDropdown.getSelectedItem();
            Pokeball pokeball = null;
            if (selectedPokeball != null) {
                try {
                    String[] pokeballData = pokeApiClient.getPokeballData(selectedPokeball);
                    pokeball = pokemonFactory.createPokeball(pokeballData[0], pokeballData[1], pokeballData[2], Integer.parseInt(pokeballData[3]));
                    loadSprite(pokeballData[1], pokeballSpriteLabel);
                    displayPokeballInfo(pokeball);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PokedexApp.this, "Error retrieving Poké Ball information", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    private void displayPokemonInfo(Pokemon pokemon) {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(pokemon.getName()).append("\n");
        info.append("HP: ").append(pokemon.getStats()[0]).append("\n");
        info.append("Attack: ").append(pokemon.getStats()[1]).append("\n");
        info.append("Defense: ").append(pokemon.getStats()[2]).append("\n");
        info.append("Special Attack: ").append(pokemon.getStats()[3]).append("\n");
        info.append("Special Defense: ").append(pokemon.getStats()[4]).append("\n");
        info.append("Speed: ").append(pokemon.getStats()[5]).append("\n");
        info.append("Types: ").append(Arrays.toString(pokemon.getTypes())).append("\n");
        info.append("Mechanic: ").append(pokemon.getMechanic());

        pokemonInfoArea.setText(info.toString());
    }

    private void displayBerryInfo(Berry berry) {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(berry.getName()).append("\n");
        info.append("Effect: ").append(berry.getEffect()).append("\n");
        info.append("Growth Time: ").append(berry.getGrowthTime());
        berryInfoArea.setText(info.toString());
    }

    private void displayPokeballInfo(Pokeball pokeball) {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(pokeball.getName()).append("\n");
        info.append("Effect: ").append(pokeball.getEffect()).append("\n");
        info.append("Price: ").append(pokeball.getPrice());
        pokeballInfoArea.setText(info.toString());
    }

    private void loadSprite(String spriteUrl, JLabel label) {
        try {
            URL url = new URL(spriteUrl);
            BufferedImage image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
