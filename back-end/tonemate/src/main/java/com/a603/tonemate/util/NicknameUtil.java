package com.a603.tonemate.util;

import com.a603.tonemate.db.entity.User;
import com.a603.tonemate.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NicknameUtil {
    private static final Random random = new Random();
    private final UserRepository userRepository;
    private String[] randomName = {"Aaron", "Aiden", "Andew", "Adrian", "Aden", "Adonis", "Agustin", "Aidan", "Aiden", "Alan", "Allan", "Albert", "Alex", "Alfredo", "Andre", "Ari", "Arnold", "Arthur", "Asher", "AxelBenny", "Bernard", "Bernardo", "Billy", "Blaze", "Braden", "Brady", "Barrett", "Brenden", "Brian", "Brooks", "Bryan", "Bryson", "Cade", "Caden", "Caiden", "Cale", "Caleb", "Calvin", "Carlo", "Carlos", "Cason", "Cesar", "Chad", "Corbin", "Chaim", "Charles", "Chaz", "Chase", "Clarence", "Coby", "Cody", "Colt", "Conner", "Connor", "Conor", "Conrad", "Cortez", "Cullen", "Curtis", "Cyrus", "Dallas", "Damian", "Damien", "Dante", "Daquan", "Darian", "Darwin", "David", "Davon", "Dean", "Deangelo", "Dennis", "Denzel", "Destin", "Devon", "Diego", "Dillan", "Dion", "Donovan", "Donte", "Dorian", "Drake", "Draven", "Drew", "Duncan", "Dustin", "Dwight", "Dylan", "Ean", "Earl", "Eddie", "Edward", "Edgar", "Edwin", "Eli", "Elijah", "Elliot", "Elliott", "Eric", "Esteban", "Estevan", "Ethan", "Eugene", "Evan", "Everett", "Ezekiel", "Ezra", "Fabian", "Felix", "Fernando", "Finn", "Forrest", "Francis", "Francisco", "Frank", "Franklin", "Freddy", "Garret", "Gary", "Geoffrey", "George", "Gerald", "Gerardo", "Gianni", "Gilbert", "Gino", "Giovani", "Grant", "Gregory", "Guillermo", "Hamza", "Harold", "Harry", "Harrison", "Hassan", "Hayden", "Hector", "Henry", "Howard", "Hugo", "Humberto", "Ian", "Ignacio", "Irvin", "Isaac", "Isaak", "Israel", "Issac", "Ivan", "Jack", "Jackson", "Jaden", "Jadon", "Jairo", "Jamal", "James", "Jared", "Jarod", "Jaron", "Jarvis", "Jason", "Jeffery", "Jeffrey", "Jerome", "Jessie", "Jimmy", "John", "Johnny", "Johnathan", "Johnathon", "Jordan", "Julian", "Julien", "Julius", "Justice", "Justus", "Justin", "Justyn", "Kaden", "Kaiden", "Kadin", "Kai", "Kale", "Kaleb", "Kamari", "Kane", "Kareem", "Keanu", "Kellen", "Kennedy", "Kenny", "Kent", "Keven", "Kevin", "Khalid", "Koby", "Konner", "Konnor", "Kurt", "Kurtis", "Kyle", "Kyler", "Lamont", "Larry", "Lawrence", "Leo", "Leonard", "Levi", "Lewis", "Louis", "Liam", "Lincoln", "Logan", "Lorenzo", "Luca", "Lucas", "Lukas", "Luke", "Malcolm", "Malik", "Manuel", "Marc", "Marcus", "Marcel", "Marcelo", "Mark", "Mario", "Marlon", "Marques", "Marquis", "Marshall", "MartinF", "Marvin", "Mason", "Mateo", "Matteo", "Mathew", "Matthew", "Maverick", "Max", "Maxim", "Maxwell", "Michael", "Micheal", "Miguel", "Mike", "Miles", "Mitchell", "Morgan", "Moshe", "Nash", "Nasir", "Nathan", "Nathen", "Neil", "Nelson", "Nestor", "Nick", "Nicholas", "Nigel", "Nikhil", "Noah", "Noe", "Noel", "Nolan", "Norman", "Oliver", "Omar", "Orion", "Orlando", "Oscar", "Owen", "Pablo", "Parker", "Patrick", "Paul", "Pedro", "Peter", "Philip", "Phillip", "Phoenix", "Porter", "PrestonQuentin", "Quinn", "Quincy", "Quintin", "Ralph", "Ramiro", "FRamon", "Randy", "Raphael", "Rashad", "Raul", "Ray", "Raymond", "Reese", "Rene", "Rex", "Rey", "Ricardo", "Richard", "Ricky", "Robert", "Roberto", "Rocky", "Rodrigo", "Rogelio", "Roger", "Rohan", "Roland", "Rolando", "Romeo", "Ronald", "Ronaldo", "Ronnie", "Roy", "Rudy", "Russell", "Ryan", "RykerSabastian", "Sage", "Salvador", "Salvatore", "Sam", "Samir", "Samuel", "Samson", "Santiago", "Santos", "Scott", "Seamus", "Sean", "Sebastian", "Sergio", "Seth", "Shane", "Silas", "Simeon", "Simon", "Skylar", "Skyler", "Solomon", "Sonny", "Soren", "Spencer", "Stanley", "Stephen", "Sterling", "Steve", "Steven", "Stuart", "Sullivan", "Tanner", "Tariq", "Tate", "Terry", "Thomas", "Tomas", "Timothy", "Titus", "Toby", "Todd", "Tommy", "Tony", "Trace", "Trever", "Trevor", "Tristan", "Turner", "Tylor", "Tyrell", "Tyson", "Ulysses", "Uriel", "Valentin", "Vaughn", "Vernon", "Vincent", "Victor", "Vincenzo", "Walker", "Walter", "Wayne", "William ", "Will", "Willie", "Wyatt", "Xander", "Xavier", "Yahir", "Yosef", "Zack", "Zander", "Zane", "Zion", "Abigail", "Alexandra", "Amelia", "Audrey", "Ava", "Alice", "Anna", "Andrea", "AmyBeatrice", "Brooke", "Bella", "Brianna", "Blair", "Bethany", "Bridget", "Bailey", "Barbara", "Bonnie", "Charlotte", "Chloe", "Caroline", "Claire", "Catherine", "Cecilia", "Cassidy", "Callie", "Cynthia", "ChristinaDaisy", "Danielle", "Delilah", "Diana", "Dominique", "Donna", "Dora", "Dulce", "Eleanor", "Emily", "Elizabeth", "Eva", "Erin", "Esther", "Eden", "Eloise", "Emma", "EuniceFaith", "Fiona", "Frances", "Freya", "Felicity", "Flora", "Fern", "Faye", "Fallon", "Farrah", "Grace", "Gabrielle", "Genevieve", "Giselle", "Georgina", "Gloria", "Gemma", "Greta", "Gwen", "GraceyHannah", "Harper", "Heather", "Hayley", "Hazel", "Hope", "Honor", "Isabella", "Imogen", "Irene", "Ingrid", "Iris", "Isla", "IdaliaJulia", "Jasmine", "Jocelyn", "Jane", "Josephine", "Jennifer", "Jaida", "Johanna", "Jolie", "JuniperKatherine", "Kara", "Keira", "Kelly", "Kristen", "KiaraLily", "Lucy", "Lila", "Lauren", "Leah", "Lillian", "Lexi", "Lois", "LinaMolly", "Morgan", "Melanie", "Mariah", "Megan", "MabelNatalie", "Nora", "Naomi", "Nicole", "Nadia", "Natasha", "Noelle", "Niamh", "Nyla", "NovaOlivia", "Ophelia", "Octavia", "Odessa", "Opal", "Orla", "Onyx", "Olive", "Oriana", "OlympiaPhoebe", "Piper", "Petra", "Patricia", "Poppy", "Pearl", "Primrose", "PalomaQueenie", "Qiana", "QuianaRachel", "Rebecca", "Rose", "Ruby", "Ramona", "Raquel", "Rhea", "Rowan", "Rosalie", "Ruby-JaneSophia", "Sarah", "Savannah", "Samantha", "Sadie", "Scarlett", "Sierra", "Skye", "Stella", "SelenaTaylor", "Trinity", "Tessa", "Tabitha", "Talia", "Tamara", "Thea", "Thalia", "Tiana", "ToriUma", "Ursula", "Una", "UnityVictoria", "Valerie", "VivianVanessa", "Violet", "Vera", "Virginia", "Vicky", "Venus", "VadaWillow", "Whitney", "Wendy", "Winona", "Winter", "Wren", "Winnie", "Wilhelmina", "WestlynXanthe", "Ximena", "Xenia", "XavieraYasminYara", "Zoe", "Zara"};
    private HashSet<String> users;

    @PostConstruct
    public void setUsers() {
        List<String> userList = userRepository.findAll().stream().map(User::getNickname).collect(Collectors.toList());
        users = new HashSet<>(userList);
    }

    //사용자 570명을 한번에 가입해야 한다면 지금의 데이터 셋으로 불가
    public String generateRandomName() {

        for (String name : randomName) {
            if (users.contains(name)) {
                continue;
            }
            return name;
        }

        //위 포문을 돌았는데 랜덤 이름을 다 쓰고도 정하지 못했다면 새로 생성해주기
        //사용자의 랜덤 닉네임 적용 시 제외
        return "a";

    }
}
