import {React, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {Button, Heading, HStack, Input, List, Spacer} from "@chakra-ui/react";
import Page from "../../components/Page";
import Card from "../../components/Card";
import MemberTable from "../projectPage/MemberTable";
import RatingRow from "./RatingRow";

export default function Rating(prop) {
    const [editMode, setEditMode] = useState(false);


    const EditButtons = () => {
        if (editMode) {
            return (
                <HStack>
                    <Button variant="whisper" size="md" onClick={() => setEditMode(false)}>
                        Cancel
                    </Button>
                    <Button variant="primary" size="md" onClick={() => setEditMode(false)}>
                        Confirm
                    </Button>
                </HStack>
            );
        } else {
            return (
                <Button variant="whisper" size="md" onClick={() => setEditMode(true)}>
                    Edit
                </Button>
            );
        }
    };

    return (
        <Page title="Ratings">
            <List spacing={2} w="full">
                <RatingRow editable={editMode}></RatingRow>
                <RatingRow editable={editMode}></RatingRow>
            </List>
            <EditButtons />
        </Page>
    );
}
