import {
    Flex,
    Box,
    Input,
    Heading,
    Editable,
    EditableInput,
    EditablePreview,
    Button,
    CircularProgress,
    CircularProgressLabel,
    Spacer,
    Textarea, List,
} from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';
import Selection from "../../components/Selection";
import {score} from "../../utils/const";
import Page from "../../components/Page";
import MemberTable from "../projectPage/MemberTable";
import ProductAreaList from "../projectPage/ProjectAreaList";
import AddMemberButton from "../projectPage/AddMemberButton";


function RatingRow({editable})
{

    return (
        <div>
            <Card
                layerStyle="card_bordered"
                justifyContent="space-between"
                // w={(parentID > 0) ? ' 90%' : 'full'}
                _hover={{ boxShadow: '2xl' }}
                align="center"
            >
                <Input
                    align="center"
                    size="md"
                    w="100%"
                    isDisabled={!editable}
                    onChange={(e) => {
                        console.log(e.target.value);
                    }}
                    value={"Frage"}
                />
            </Card>
            <Card
                layerStyle="card_bordered"
                justifyContent="space-between"
                // w={(parentID > 0) ? ' 90%' : 'full'}
                _hover={{ boxShadow: '2xl' }}
            >
                <Spacer />
                <Textarea width="100%" placeholder="Antwort" />
                <Spacer />
                <Selection options={['gering', 'mittel', 'hoch']} selected={'gering'}></Selection>
                <Spacer />
                <Textarea width="100%" placeholder="Anmerkung" />
                <Spacer />
                <Input
                    align="center"
                    size="md"
                    w="25%"
                    isDisabled={!editable}
                    onChange={(e) => {
                        console.log(e.target.value);
                    }}
                    value={"Upload"}
                />
            </Card>
        </div>
    );
}

export default function RatingTable({editMode,ratings, handleChange})
{
    const handleScoreChange = (rating) => (newRating) => {
        // This is a curried function in JS
        let index = ratings.map((r) => r.rating).indexOf(rating.rating);
        ratings[index] = { ...ratings, score: newRating };
        handleChange(ratings);
    };

    return (
        <List spacing={2} direction="column" w="full" align="center" >
            <RatingRow editable={editMode}></RatingRow>
            {ratings.map((rating) => (
                <Flex gridGap={3}>
                    <RatingRow
                        editMode={editMode}
                    ></RatingRow>
                </Flex>
            ))}
        </List>
    );
}